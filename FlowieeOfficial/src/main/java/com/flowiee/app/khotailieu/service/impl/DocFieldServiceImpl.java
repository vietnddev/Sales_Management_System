package com.flowiee.app.khotailieu.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.hethong.model.action.KhoTaiLieuAction;
import com.flowiee.app.hethong.model.module.SystemModule;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.khotailieu.entity.DocData;
import com.flowiee.app.khotailieu.entity.DocField;
import com.flowiee.app.khotailieu.entity.Document;
import com.flowiee.app.khotailieu.repository.DocFieldRepository;
import com.flowiee.app.khotailieu.service.DocDataService;
import com.flowiee.app.khotailieu.service.DocFieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocFieldServiceImpl implements DocFieldService {
    public static final Logger logger = LoggerFactory.getLogger(DocFieldServiceImpl.class);
    private static final String module = SystemModule.KHO_TAI_LIEU.name();

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
    public List<DocField> findByDocTypeId(LoaiTaiLieu loaiTaiLieu) {
        return docFieldRepository.findByLoaiTaiLieu(loaiTaiLieu);
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
            systemLogService.writeLog(module, KhoTaiLieuAction.DOCTYPE_CONFIG_DOCUMENT.name(), "Thêm mới doc_field: " + docField.toString());
            logger.info(DocumentServiceImpl.class.getName() + ": Thêm mới doc_field " + docField.toString());
        } catch (Exception e) {
            logger.error("An error occurred while insert new docField!", e.getCause().toString());
        }
        return "OK";
    }

    @Override
    public String update(DocField docField, Integer docFieldId) {
        if (docField == null || docFieldId == null || docFieldId <= 0) {
            throw new NotFoundException();
        }
        docField.setId(docFieldId);
        docFieldRepository.save(docField);
        systemLogService.writeLog(module, KhoTaiLieuAction.DOCTYPE_CONFIG_DOCUMENT.name(), "Cập nhật doc_field: " + docField.toString());
        logger.info(DocumentServiceImpl.class.getName() + ": Cập nhật doc_field " + docFieldId.toString());
        return "OK";
    }

    @Override
    public DocField delete(Integer id) {
        DocField docFieldToDelete = findById(id);
        if (docFieldToDelete != null) {
            docFieldRepository.deleteById(id);
            systemLogService.writeLog(module, KhoTaiLieuAction.DOCTYPE_CONFIG_DOCUMENT.name(), "Xóa doc_field: " + docFieldToDelete.toString());
            logger.info(DocumentServiceImpl.class.getName() + ": Xóa doc_field " + docFieldToDelete.toString());
            return docFieldToDelete;
        } else {
            throw new NotFoundException();
        }
    }
}