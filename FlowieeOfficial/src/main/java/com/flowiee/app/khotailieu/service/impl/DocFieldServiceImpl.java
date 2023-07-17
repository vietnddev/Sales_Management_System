package com.flowiee.app.khotailieu.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
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

    @Autowired
    private DocFieldRepository docFieldRepository;
    @Autowired
    private DocDataService docDataService;

    @Override
    public List<DocField> findAll() {
        return docFieldRepository.findAll();
    }

    @Override
    public DocField findById(int id) {
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
        } catch (Exception e) {
            logger.error("Có lỗi xảy ra khi thêm mới docField!", e.getCause().toString());
        }
        return "OK";
    }

    @Override
    public String update(DocField docField, int docFieldId) {
        return null;
    }

    @Override
    public DocField delete(int id) {
        DocField docFieldToDelete = findById(id);
        if (docFieldToDelete != null) {
            docFieldRepository.deleteById(id);
            return docFieldToDelete;
        } else {
            throw new NotFoundException();
        }
    }
}