package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.Material;
import com.flowiee.app.repository.product.MaterialRepository;
import com.flowiee.app.service.product.MaterialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    @Override
    public Material findById(Integer entityId) {
        return materialRepository.findById(entityId).get();
    }

    @Override
    public String save(Material entity) {
        materialRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(Material entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        materialRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        Material material = this.findById(entityId);
        materialRepository.deleteById(entityId);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public List<Material> findByCode(String code) {
        return materialRepository.findByCode(code);
    }

    @Override
    public List<Material> findByImportId(Integer importId) {
        List<Material> listData = new ArrayList<>();
        if (importId != null && importId > 0) {
            listData = materialRepository.findByImportId(importId);
        }
        return listData;
    }
}