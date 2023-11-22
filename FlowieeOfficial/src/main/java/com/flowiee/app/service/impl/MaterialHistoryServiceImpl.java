package com.flowiee.app.service.impl;

import com.flowiee.app.entity.MaterialHistory;
import com.flowiee.app.repository.MaterialHistoryRepository;
import com.flowiee.app.service.MaterialHistoryService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialHistoryServiceImpl implements MaterialHistoryService {
    @Autowired
    private MaterialHistoryRepository materialHistoryRepository;

    @Override
    public List<MaterialHistory> findAll() {
        return materialHistoryRepository.findAll();
    }

    @Override
    public MaterialHistory findById(Integer entityId) {
        return materialHistoryRepository.findById(entityId).get();
    }

    @Override
    public String save(MaterialHistory entity) {
        if (entity == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        materialHistoryRepository.save(entity);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(MaterialHistory entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        materialHistoryRepository.save(entity);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        materialHistoryRepository.deleteById(entityId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public List<MaterialHistory> findByMaterialId(Integer materialId) {
        return materialHistoryRepository.findByMaterialId(materialId);
    }

    @Override
    public List<MaterialHistory> findByAction(String action) {
        return materialHistoryRepository.findByAction(action);
    }
}