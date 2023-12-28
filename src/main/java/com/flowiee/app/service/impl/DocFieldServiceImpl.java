package com.flowiee.app.service.impl;

import com.flowiee.app.exception.DataInUseException;
import com.flowiee.app.model.role.SystemAction.StorageAction;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.entity.DocData;
import com.flowiee.app.entity.DocField;
import com.flowiee.app.entity.Document;
import com.flowiee.app.repository.DocFieldRepository;
import com.flowiee.app.service.DocDataService;
import com.flowiee.app.service.DocFieldService;
import com.flowiee.app.service.SystemLogService;

import com.flowiee.app.utils.ErrorMessages;
import com.flowiee.app.utils.MessagesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocFieldServiceImpl implements DocFieldService {
    public static final Logger logger = LoggerFactory.getLogger(DocFieldServiceImpl.class);
    private static final String module = SystemModule.STORAGE.name();

    @Autowired
    private DocFieldRepository docFieldRepository;
    @Autowired
    private DocDataService docDataService;
    @Autowired
    private SystemLogService systemLogService;

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
    public String save(DocField docField) {
        try {
            docFieldRepository.save(docField);
            List<Document> listDocumentInUsed = docField.getLoaiTaiLieu().getListDocument();
            for (Document document : listDocumentInUsed) {
                DocData docData = new DocData();
                docData.setId(0);
                docData.setDocField(docField);
                docData.setNoiDung(null);
                docData.setDocument(document);
                docDataService.save(docData);
            }
            systemLogService.writeLog(module, StorageAction.STG_DOC_DOCTYPE_CONFIG.name(), "Thêm mới doc_field: " + docField.toString());
            logger.info(DocumentServiceImpl.class.getName() + ": Thêm mới doc_field " + docField.toString());
        } catch (Exception e) {
            logger.error("An error occurred while insert new docField!", e.getCause().toString());
        }
        return "OK";
    }

    @Override
    public String update(DocField docField, Integer docFieldId) {
        docField.setId(docFieldId);
        docFieldRepository.save(docField);
        systemLogService.writeLog(module, StorageAction.STG_DOC_DOCTYPE_CONFIG.name(), "Cập nhật doc_field: " + docField.toString());
        logger.info(DocumentServiceImpl.class.getName() + ": Cập nhật doc_field " + docFieldId.toString());
        return "OK";
    }

    @Transactional
    @Override
    public DocField delete(Integer id) {
        DocField docFieldToDelete = findById(id);
        if (!docDataService.findByDocField(id).isEmpty()) {
            throw new DataInUseException(ErrorMessages.ERROR_LOCKED);
        }
        docFieldRepository.deleteById(id);
        systemLogService.writeLog(module, StorageAction.STG_DOC_DOCTYPE_CONFIG.name(), "Xóa doc_field: " + docFieldToDelete.toString());
        logger.info(DocumentServiceImpl.class.getName() + ": Xóa doc_field " + docFieldToDelete.toString());
        return docFieldToDelete;
    }
}