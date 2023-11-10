package com.flowiee.app.service.impl;

import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.action.KhoTaiLieuAction;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.entity.storage.DocData;
import com.flowiee.app.entity.storage.Document;
import com.flowiee.app.model.storage.DocMetaResponse;
import com.flowiee.app.model.storage.DocumentType;
import com.flowiee.app.repository.storage.DocumentRepository;
import com.flowiee.app.service.storage.DocDataService;
import com.flowiee.app.service.storage.DocumentService;
import com.flowiee.app.service.system.SystemLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
public class DocumentServiceImpl implements DocumentService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);
    private static final String module = SystemModule.KHO_TAI_LIEU.name();

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocDataService docDataService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<Document> findRootDocument() {
        List<Document> listDocument = documentRepository.findRootDocument();
        return listDocument;
    }

    @Override
    public List<Document> findRootFolder() {
        List<Document> listRootFolder = documentRepository.findRootFolder();
        return listRootFolder;
    }

    @Override
    public List<Document> findRootFile() {
        List<Document> listRootFile = documentRepository.findRootFile();
        return listRootFile;
    }

    @Override
    public List<Document> findDocumentByParentId(Integer parentId) {
        return documentRepository.findListDocumentByParentId(parentId);
    }

    @Override
    public List<Document> findFolderByParentId(Integer parentId) {
        return documentRepository.findListFolderByParentId(parentId);
    }

    @Override
    public List<Document> findFileByParentId(Integer parentId) {
        return documentRepository.findListFileByParentId(parentId);
    }

    @Override
    public List<Document> findAllFolder() {
        return documentRepository.findAllFolder(DocumentType.FOLDER.name());
    }

    @Override
    public Document findById(Integer id) {
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public Document save(Document document) {
        systemLogService.writeLog(module, KhoTaiLieuAction.CREATE_DOCUMENT.name(), "Thêm mới tài liệu: " + document.toString());
        logger.info(DocumentServiceImpl.class.getName() + ": Thêm mới tài liệu " + document.toString());
        return documentRepository.save(document);
    }

    @Override
    public String update(Document data, Integer documentId) {
        Document document = this.findById(documentId);
        if (document != null) {
            document.setTen(data.getTen());
            document.setMoTa(data.getMoTa());
            documentRepository.save(document);
            systemLogService.writeLog(module, KhoTaiLieuAction.UPDATE_DOCUMENT.name(), "Cập nhật tài liệu: " + document.toString());
            logger.info(DocumentServiceImpl.class.getName() + ": Cập nhật tài liệu " + document.toString());
            return "OK";
        }
        return "NOK";
    }

    @Override
    public String updateMetadata(Integer[] docDataIds, String[] docDataValues, Integer documentId) {
        Document document = this.findById(documentId);
        if (document == null) {
            throw new BadRequestException();
        }
        for (Integer i = 0; i < docDataIds.length; i++) {
            DocData docData = docDataService.findById(docDataIds[i]);
            if (docData != null) {
                docData.setNoiDung(docDataValues[i]);
                docDataService.save(docData);
            }
        }
        systemLogService.writeLog(module, KhoTaiLieuAction.UPDATE_DOCUMENT.name(), "Update metadata: " + document.toString());
        logger.info(DocumentServiceImpl.class.getName() + ": Update metadata " + document.toString());
        return "OK";
    }

    @Transactional
    @Override
    public String delete(Integer id) {
        Document document = this.findById(id);
        if (document != null) {
            documentRepository.deleteById(id);
            systemLogService.writeLog(module, KhoTaiLieuAction.DELETE_DOCUMENT.name(), "Xóa tài liệu: " + document.toString());
            logger.info(DocumentServiceImpl.class.getName() + ": Xóa tài liệu " + document.toString());
            return "OK";
        } else {
            return "NOK";
        }
    }

    @Override
    public List<DocMetaResponse> getMetadata(Integer documentId) {
        List<DocMetaResponse> listReturn = new ArrayList<>();

        Query result = entityManager.createQuery("SELECT d.id, d.noiDung, f.tenField, f.loaiField, f.batBuocNhap " +
                                                        "FROM DocField f " +
                                                        "LEFT JOIN DocData d ON f.id = d.docField.id " +
                                                        "WHERE d.document.id = " + documentId);
        List<Object[]> listData = result.getResultList();

        if (!listData.isEmpty()) {
            for (Object[] data : listData) {
                DocMetaResponse metadata = new DocMetaResponse();
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
        return documentRepository.findDocumentByDocTypeId(docTypeId);
    }
}