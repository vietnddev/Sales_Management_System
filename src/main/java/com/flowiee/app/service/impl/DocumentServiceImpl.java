package com.flowiee.app.service.impl;

import com.flowiee.app.dto.DocumentDTO;
import com.flowiee.app.entity.DocField;
import com.flowiee.app.entity.FileStorage;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.entity.DocData;
import com.flowiee.app.entity.Document;
import com.flowiee.app.dto.DocMetaDTO;
import com.flowiee.app.repository.DocumentRepository;
import com.flowiee.app.service.*;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtils;
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
import org.springframework.web.servlet.ModelAndView;

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
    public Page<Document> findRootDocument(Integer pageSize, Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("isFolder").ascending());
        return documentRepo.findAll(0, pageable);
    }

    @Override
    public Page<Document> findSubDocument(Integer pageSize, Integer pageNum, Integer parentId) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("isFolder").ascending());
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
        for (Integer i = 0; i < docDataIds.length; i++) {
            DocData docData = docDataService.findById(docDataIds[i]);
            if (docData != null) {
                docData.setNoiDung(docDataValues[i]);
                docDataService.save(docData);
            }
        }
        systemLogService.writeLog(module, AppConstants.STORAGE_ACTION.STG_DOC_UPDATE.name(), "Update metadata: " + document.toString());
        logger.info(DocumentServiceImpl.class.getName() + ": Update metadata " + document.toString());
        return "OK";
    }

    @Transactional
    @Override
    public String delete(Integer documentId) {
        Document document = this.findById(documentId);
        if (document != null) {
            documentRepo.deleteById(documentId);
            systemLogService.writeLog(module, AppConstants.STORAGE_ACTION.STG_DOC_DELETE.name(), "Xóa tài liệu: " + document.toString());
            logger.info(DocumentServiceImpl.class.getName() + ": Xóa tài liệu " + document.toString());
            return "OK";
        } else {
            return "NOK";
        }
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
}