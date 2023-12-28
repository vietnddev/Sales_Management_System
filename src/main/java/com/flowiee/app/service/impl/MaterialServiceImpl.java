package com.flowiee.app.service.impl;

import com.flowiee.app.entity.Category;
import com.flowiee.app.entity.CategoryHistory;
import com.flowiee.app.entity.Material;
import com.flowiee.app.entity.MaterialHistory;
import com.flowiee.app.repository.MaterialRepository;
import com.flowiee.app.service.MaterialHistoryService;
import com.flowiee.app.service.MaterialService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialHistoryService materialHistoryService;

    @Override
    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    @Override
    public List<Material> findAll(Integer ticketImportId, Integer supplierId, Integer unitId, String code, String name, String location, String status) {
        return materialRepository.findAll(ticketImportId, supplierId, unitId, code, name, location, status);
    }

    @Override
    public Material findById(Integer entityId) {
        return materialRepository.findById(entityId).orElse(null);
    }

    @Override
    public String save(Material entity) {
        materialRepository.save(entity);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(Material entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        Material materialBefore = this.findById(entityId);
        materialBefore.compareTo(entity).forEach((key, value) -> {
            MaterialHistory categoryHistory = new MaterialHistory();
            categoryHistory.setTitle("Update material");
            categoryHistory.setMaterial(new Material(entityId));
            categoryHistory.setFieldName(key);
            categoryHistory.setOldValue(value.substring(0, value.indexOf("#")));
            categoryHistory.setNewValue(value.substring(value.indexOf("#") + 1));
            materialHistoryService.save(categoryHistory);
        });
        entity.setId(entityId);
        materialRepository.save(entity);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        materialRepository.deleteById(entityId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
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

    @Override
    public List<Material> findByUnit(Integer unitId) {
        return materialRepository.findByUnit(unitId);
    }
}