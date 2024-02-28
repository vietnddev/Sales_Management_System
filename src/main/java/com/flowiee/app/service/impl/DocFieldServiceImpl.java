package com.flowiee.app.service.impl;

import com.flowiee.app.exception.DataInUseException;
import com.flowiee.app.entity.DocField;
import com.flowiee.app.repository.DocFieldRepository;
import com.flowiee.app.service.DocDataService;
import com.flowiee.app.service.DocFieldService;
import com.flowiee.app.service.SystemLogService;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocFieldServiceImpl implements DocFieldService {
    private static final String module = AppConstants.SYSTEM_MODULE.STORAGE.name();

    private final DocFieldRepository docFieldRepository;
    private final DocDataService docDataService;
    private final SystemLogService systemLogService;

    @Autowired
    public DocFieldServiceImpl(DocFieldRepository docFieldRepository, DocDataService docDataService, SystemLogService systemLogService) {
        this.docFieldRepository = docFieldRepository;
        this.docDataService = docDataService;
        this.systemLogService = systemLogService;
    }

    @Override
    public List<DocField> findAll() {
        return docFieldRepository.findAll();
    }

    @Override
    public DocField findById(Integer id) {
        return docFieldRepository.findById(id).orElse(null);
    }

    @Override
    public List<DocField> findByDocTypeId(Integer doctypeId) {
        return docFieldRepository.findByDoctype(doctypeId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public DocField save(DocField docField) {
        DocField docFieldSaved = docFieldRepository.save(docField);
//        List<Document> listDocumentInUsed = docField.getDocType().getListDocument();
//        for (Document document : listDocumentInUsed) {
//            DocData docData = new DocData();
//            docData.setId(0);
//            docData.setDocField(docField);
//            docData.setValue(null);
//            docData.setDocument(document);
//            docDataService.save(docData);
//        }
        systemLogService.writeLog(module, AppConstants.STORAGE_ACTION.STG_DOC_DOCTYPE_CONFIG.name(), "Thêm mới doc_field: " + docField.toString());
        logger.info(DocumentServiceImpl.class.getName() + ": Thêm mới doc_field " + docField.toString());
        return docFieldSaved;
    }

    @Override
    public DocField update(DocField docField, Integer docFieldId) {
        docField.setId(docFieldId);
        DocField docFieldUpdated = docFieldRepository.save(docField);
        systemLogService.writeLog(module, AppConstants.STORAGE_ACTION.STG_DOC_DOCTYPE_CONFIG.name(), "Cập nhật doc_field: " + docField.toString());
        logger.info(DocumentServiceImpl.class.getName() + ": Cập nhật doc_field " + docFieldId.toString());
        return docFieldUpdated;
    }

    @Transactional
    @Override
    public String delete(Integer id) {
        if (!docDataService.findByDocField(id).isEmpty()) {
            throw new DataInUseException(MessageUtils.ERROR_DATA_LOCKED);
        }
        docFieldRepository.deleteById(id);
        systemLogService.writeLog(module, AppConstants.STORAGE_ACTION.STG_DOC_DOCTYPE_CONFIG.name(), "Xóa doc_field id=" + id);
        logger.info(DocumentServiceImpl.class.getName() + ": Xóa doc_field id=" + id);
        return MessageUtils.DELETE_SUCCESS;
    }
}