package com.flowiee.app.khotailieu.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.khotailieu.entity.DocField;
import com.flowiee.app.khotailieu.repository.DocFieldRepository;
import com.flowiee.app.khotailieu.service.DocFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocFieldServiceImpl implements DocFieldService {
    @Autowired
    private DocFieldRepository docFieldRepository;

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

    @Override
    public DocField save(DocField docField) {
        return docFieldRepository.save(docField);
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