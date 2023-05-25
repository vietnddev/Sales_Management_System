package com.flowiee.app.khotailieu.service;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.khotailieu.entity.DocField;
import com.flowiee.app.khotailieu.repository.DocFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocFieldService {
    @Autowired
    private DocFieldRepository docFieldRepository;

    public List<DocField> findAll() {
        return docFieldRepository.findAll();
    }

    public DocField findById(int id) {
        return docFieldRepository.findById(id).orElse(null);
    }

    public List<DocField> findByDocTypeId(LoaiTaiLieu loaiTaiLieu) {
        return docFieldRepository.findByLoaiTaiLieu(loaiTaiLieu);
    }

    public DocField save(DocField docField) {
        return docFieldRepository.save(docField);
    }

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
