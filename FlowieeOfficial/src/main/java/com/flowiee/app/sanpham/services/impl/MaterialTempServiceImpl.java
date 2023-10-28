package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.sanpham.repository.MaterialTempRepository;
import com.flowiee.app.sanpham.services.MaterialTempService;
import com.flowiee.app.storage.entity.MaterialTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialTempServiceImpl implements MaterialTempService {
    @Autowired
    private MaterialTempRepository materialTempRepository;

    @Override
    public List<MaterialTemp> findAll() {
        return materialTempRepository.findAll();
    }

    @Override
    public MaterialTemp findById(Integer entityId) {
        return materialTempRepository.findById(entityId).get();
    }

    @Override
    public String save(MaterialTemp entity) {
        materialTempRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(MaterialTemp entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        materialTempRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        MaterialTemp material = this.findById(entityId);
        materialTempRepository.deleteById(entityId);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public List<MaterialTemp> findByCode(String code) {
        return materialTempRepository.findByCode(code);
    }

    @Override
    public List<MaterialTemp> findByImportId(Integer importId) {
        List<MaterialTemp> listData = new ArrayList<>();
        if (importId != null && importId > 0) {
            listData = materialTempRepository.findByImportId(importId);
        }
        return listData;
    }
}