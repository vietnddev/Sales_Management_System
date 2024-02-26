package com.flowiee.app.service.impl;

import com.flowiee.app.dto.DocumentDTO;
import com.flowiee.app.entity.DocField;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.entity.DocData;
import com.flowiee.app.entity.Document;
import com.flowiee.app.dto.DocMetaDTO;
import com.flowiee.app.repository.DocumentRepository;
import com.flowiee.app.service.*;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtils;
import com.flowiee.app.utils.MessageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.util.*;

@Service
public class DocumentServiceImpl implements DocumentService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);
    private static final String module = AppConstants.SYSTEM_MODULE.STORAGE.name();

    private final DocumentRepository documentRepo;
    private final DocDataService docDataService;
    private final DocFieldService docFieldService;
    private final EntityManager entityManager;
    private final SystemLogService systemLogService;
    private final FileStorageService fileService;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepo, DocDataService docDataService, EntityManager entityManager,
                               SystemLogService systemLogService, FileStorageService fileService, DocFieldService docFieldService) {
        this.documentRepo = documentRepo;
        this.docDataService = docDataService;
        this.entityManager = entityManager;
        this.systemLogService = systemLogService;
        this.fileService = fileService;
        this.docFieldService = docFieldService;
    }

    @Override
    public Page<Document> findDocuments(Integer pageSize, Integer pageNum, Integer parentId) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("isFolder", "createdAt").descending());
        return documentRepo.findAll(parentId, pageable);
    }

    @Override
    public List<Document> findDocumentByParentId(Integer parentId) {
        return documentRepo.findListDocumentByParentId(parentId);
    }

    @Override
    public List<Document> findFolderByParentId(Integer parentId) {
        return documentRepo.findListFolderByParentId(parentId);
    }

    @Override
    public List<Document> findFileByParentId(Integer parentId) {
        return documentRepo.findListFileByParentId(parentId);
    }

    @Override
    public List<Document> findAllFolder() {
        return documentRepo.findAllFolder(AppConstants.DOCUMENT_TYPE.FO.name());
    }

    @Override
    public Document findById(Integer id) {
        return documentRepo.findById(id).orElse(null);
    }

    @Override
    public Document save(Document document) {
        return null;
    }

    @Override
    public Document update(Document data, Integer documentId) {
        Document document = this.findById(documentId);
        if (document == null) {
            throw new BadRequestException();
        }
        document.setName(data.getName());
        document.setDescription(data.getDescription());
        systemLogService.writeLog(module, AppConstants.STORAGE_ACTION.STG_DOC_UPDATE.name(), "Cập nhật tài liệu: " + document.toString());
        logger.info(DocumentServiceImpl.class.getName() + ": Cập nhật tài liệu " + document.toString());
        return documentRepo.save(document);
    }

    @Override
    public String updateMetadata(Integer[] docDataIds, String[] docDataValues, Integer documentId) {
        Document document = this.findById(documentId);
        for (int i = 0; i < docDataIds.length; i++) {
            DocData docData = docDataService.findById(docDataIds[i]);
            if (docData != null) {
                docData.setNoiDung(docDataValues[i]);
                docDataService.save(docData);
            }
        }
        systemLogService.writeLog(module, AppConstants.STORAGE_ACTION.STG_DOC_UPDATE.name(), "Update metadata: " + document.toString());
        logger.info(DocumentServiceImpl.class.getName() + ": Update metadata " + document.toString());
        return MessageUtils.UPDATE_SUCCESS;
    }

    @Transactional
    @Override
    public String delete(Integer documentId) {
        Document document = this.findById(documentId);
        if (document == null) {
            throw new BadRequestException();
        }
        documentRepo.deleteById(documentId);
        systemLogService.writeLog(module, AppConstants.STORAGE_ACTION.STG_DOC_DELETE.name(), "Xóa tài liệu: " + document.toString());
        logger.info(DocumentServiceImpl.class.getName() + ": Xóa tài liệu " + document.toString());
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public List<DocMetaDTO> getMetadata(Integer documentId) {
        List<DocMetaDTO> listReturn = new ArrayList<>();

        Query result = entityManager.createQuery("SELECT d.id, d.noiDung, f.tenField, f.loaiField, f.batBuocNhap " +
                                                        "FROM DocField f " +
                                                        "LEFT JOIN DocData d ON f.id = d.docField.id " +
                                                        "WHERE d.document.id = " + documentId);
        @SuppressWarnings("unchecked")
		List<Object[]> listData = result.getResultList();

        if (!listData.isEmpty()) {
            for (Object[] data : listData) {
                DocMetaDTO metadata = new DocMetaDTO();
                metadata.setDocDataId(Integer.parseInt(String.valueOf(data[0])));
                metadata.setDocDataValue(data[1] != null ? String.valueOf(data[1]) : "");
                metadata.setDocFieldName(String.valueOf(data[2]));
                metadata.setDocFieldTypeInput(String.valueOf(data[3]));
                metadata.setDocFieldRequired(String.valueOf(data[4]).equals("1") ? true : false);
                listReturn.add(metadata);
            }
        }

        return listReturn;
    }

    @Override
    public List<Document> findByDoctype(Integer docTypeId) {
        return documentRepo.findDocumentByDocTypeId(docTypeId);
    }

    @Override
    public DocumentDTO save(DocumentDTO documentDTO) {
        try {
            Document document = Document.fromDocumentDTO(documentDTO);
            document.setAsName(CommonUtils.generateAliasName(document.getName()));
            if (ObjectUtils.isEmpty(document.getParentId())) {
                document.setParentId(0);
            }
            Document documentSaved = documentRepo.save(document);
            if ("N".equals(document.getIsFolder()) && documentDTO.getFileUpload() != null) {
                fileService.saveFileOfDocument(documentDTO.getFileUpload(), documentSaved.getId());
                //Default docData
                if (documentSaved.getLoaiTaiLieu() != null) {
                    for (DocField docField : docFieldService.findByDocTypeId(document.getLoaiTaiLieu().getId())) {
                        DocData docData = DocData.builder()
                                                 .docField(new DocField(docField.getId()))
                                                 .document(new Document(documentSaved.getId()))
                                                 .noiDung("").build();
                        docDataService.save(docData);
                    }
                }
            }
            systemLogService.writeLog(module, AppConstants.STORAGE_ACTION.STG_DOC_CREATE.name(), "Thêm mới tài liệu: " + document.toString());
            logger.info(DocumentServiceImpl.class.getName() + ": Thêm mới tài liệu " + document.toString());
            return DocumentDTO.fromDocument(documentSaved);
        } catch (RuntimeException | IOException ex) {
            throw new AppException(ex);
        }
    }

    @Override
    public DocumentDTO update(DocumentDTO documentDTO, Integer documentId) {
        return null;
    }

    @Override
    public List<DocumentDTO> findHierarchyOfDocument(Integer documentId, Integer parentId) {
        List<DocumentDTO> hierarchy = new ArrayList<>();
        String strSQL = "WITH DocumentHierarchy(ID, NAME, AS_NAME, PARENT_ID, H_LEVEL) AS ( " +
                        "    SELECT ID, NAME, AS_NAME, PARENT_ID, 1 " +
                        "    FROM STG_DOCUMENT " +
                        "    WHERE id = ? " +
                        "    UNION ALL " +
                        "    SELECT d.ID, d.NAME, d.AS_NAME ,d.PARENT_ID, dh.H_LEVEL + 1 " +
                        "    FROM STG_DOCUMENT d " +
                        "    INNER JOIN DocumentHierarchy dh ON dh.PARENT_ID = d.id " +
                        "), " +
                        "DocumentToFindParent(ID, NAME, AS_NAME, PARENT_ID, H_LEVEL) AS ( " +
                        "    SELECT ID, NAME, AS_NAME, PARENT_ID, NULL AS H_LEVEL " +
                        "    FROM STG_DOCUMENT " +
                        "    WHERE ID = ? " +
                        ") " +
                        "SELECT ID, NAME, CONCAT(CONCAT(AS_NAME, '-'), ID) AS AS_NAME, PARENT_ID, H_LEVEL " +
                        "FROM DocumentHierarchy " +
                        "UNION ALL " +
                        "SELECT ID, NAME, CONCAT(CONCAT(AS_NAME, '-'), ID) AS AS_NAME, PARENT_ID, H_LEVEL " +
                        "FROM DocumentToFindParent " +
                        "START WITH ID = ? " +
                        "CONNECT BY PRIOR PARENT_ID = ID " +
                        "ORDER BY H_LEVEL DESC";
        logger.info("Load hierarchy of document (breadcrumb)");
        Query query = entityManager.createNativeQuery(strSQL);
        query.setParameter(1, documentId);
        query.setParameter(2, documentId);
        query.setParameter(3, parentId);
        @SuppressWarnings("unchecked")
        List<Object[]> list = query.getResultList();
        DocumentDTO rootHierarchy = new DocumentDTO();
        rootHierarchy.setId(null);
        rootHierarchy.setName("Home");
        rootHierarchy.setAliasName("");
        hierarchy.add(rootHierarchy);
        for (Object[] doc : list) {
            DocumentDTO docDTO = new DocumentDTO();
            docDTO.setId(Integer.parseInt(String.valueOf(doc[0])));
            docDTO.setName(String.valueOf(doc[1]));
            docDTO.setAliasName(String.valueOf(doc[2]));
            docDTO.setParentId(Integer.parseInt(String.valueOf(doc[3])));
            hierarchy.add(docDTO);
        }
        return hierarchy;
    }

    @Override
    public List<DocumentDTO> generateFolderTree() {
        List<DocumentDTO> folderTree = new ArrayList<>();
        String strSQL = "WITH DocumentHierarchy(ID, NAME, AS_NAME, PARENT_ID, IS_FOLDER, Path, HierarchyLevel) AS ( " +
                        "    SELECT ID, NAME, AS_NAME, PARENT_ID, IS_FOLDER, CAST(NAME AS VARCHAR2(4000)) AS Path, 0 AS HierarchyLevel " +
                        "    FROM STG_DOCUMENT " +
                        "    WHERE PARENT_ID = 0 AND IS_FOLDER = 'Y' " +
                        "    UNION ALL " +
                        "    SELECT d.ID, d.NAME, d.AS_NAME, d.PARENT_ID, d.IS_FOLDER, dh.Path || '/' || d.NAME || '' || ' ', dh.HierarchyLevel + 1 " +
                        "    FROM STG_DOCUMENT d " +
                        "    INNER JOIN DocumentHierarchy dh ON d.PARENT_ID = dh.ID " +
                        "    WHERE d.IS_FOLDER = 'Y' " +
                        ") " +
                        ", RecursiveHierarchy AS ( " +
                        "    SELECT ID, NAME, AS_NAME, PARENT_ID, IS_FOLDER, HierarchyLevel, Path, " +
                        "           ROW_NUMBER() OVER (PARTITION BY SUBSTR(Path, 1, INSTR(Path, '/', -1) - 1) ORDER BY Path) AS RowNumm " +
                        "    FROM DocumentHierarchy " +
                        "), SubFolderList AS ( " +
                        "    SELECT dh.PARENT_ID AS Parent_ID, " +
                        "           LISTAGG(dh.ID, '|') WITHIN GROUP (ORDER BY dh.ID) AS SubFoldersId " +
                        "    FROM " +
                        "        DocumentHierarchy dh " +
                        "    GROUP BY dh.PARENT_ID " +
                        ") " +
                        "SELECT rh.ID, " +
                        "       rh.NAME, " +
                        "       rh.AS_NAME, " +
                        "       rh.PARENT_ID, " +
                        "       CASE WHEN EXISTS (SELECT 1 FROM STG_DOCUMENT sub WHERE sub.PARENT_ID = rh.ID) THEN 'Y' ELSE 'N' END AS Has_SubFolders, " +
                        "       sf.SubFoldersId, " +
                        "       rh.HierarchyLevel, " +
                        "       rh.RowNumm, " +
                        "       RTRIM(rh.Path) " +
                        "FROM RecursiveHierarchy rh " +
                        "LEFT JOIN SubFolderList sf ON rh.ID = sf.Parent_ID " +
                        "ORDER BY rh.Path";
        //HierarchyLevel: Thư mục ở cấp thứ mấy
        //RowNumm: Thư mục số mấy của cấp HierarchyLevel
        logger.info("Generate folder tree");
        Query query = entityManager.createNativeQuery(strSQL);
        @SuppressWarnings("unchecked")
        List<Object[]> list = query.getResultList();
        for (Object[] doc : list) {
            DocumentDTO docDTO = new DocumentDTO();
            docDTO.setId(Integer.parseInt(String.valueOf(doc[0])));
            docDTO.setName(String.valueOf(doc[1]));
            docDTO.setAliasName(String.valueOf(doc[2]));
            docDTO.setParentId(Integer.parseInt(String.valueOf(doc[3])));
            docDTO.setHasSubFolder(String.valueOf(doc[4]));
            folderTree.add(docDTO);
        }

        for (int i = 0; i < folderTree.size(); i++) {
            if (folderTree.get(i).getHasSubFolder().equals("Y")) {
                List<Integer> subFolderIds = new ArrayList<>();
                if (list.get(i)[5] != null) {
                    for (String subId : list.get(i)[5].toString().split("\\|")) {
                        subFolderIds.add(Integer.parseInt(subId));
                    }
                }
                folderTree.get(i).setSubFolders(this.findSubFolders(folderTree, subFolderIds));
            }
        }

        return folderTree;
    }

    private List<DocumentDTO> findSubFolders(List<DocumentDTO> lsFolders, List<Integer> subFolderId) {
        List<DocumentDTO> listSubFolders = new ArrayList<>();
        for (DocumentDTO dto : lsFolders) {
            if (listSubFolders.size() == subFolderId.size()) {
                break;
            }
            if (subFolderId.contains(dto.getId())) {
                listSubFolders.add(dto);
                //System.out.println("Sub " + dto.getName());
            }
        }
        return listSubFolders;
    }
}