package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.Material;
import com.flowiee.pms.entity.product.MaterialHistory;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.product.MaterialHistoryRepository;
import com.flowiee.pms.service.product.MaterialHistoryService;

import com.flowiee.pms.utils.constants.MessageCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MaterialHistoryServiceImpl implements MaterialHistoryService {
    private final MaterialHistoryRepository materialHistoryRepo;

    public MaterialHistoryServiceImpl(MaterialHistoryRepository materialHistoryRepo) {
        this.materialHistoryRepo = materialHistoryRepo;
    }

    @Override
    public List<MaterialHistory> findAll() {
        return materialHistoryRepo.findAll();
    }

    @Override
    public Optional<MaterialHistory> findById(Integer entityId) {
        return materialHistoryRepo.findById(entityId);
    }

    @Override
    public MaterialHistory save(MaterialHistory entity) {
        if (entity == null) {
            throw new BadRequestException();
        }
        return materialHistoryRepo.save(entity);
    }

    @Override
    public MaterialHistory update(MaterialHistory entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return materialHistoryRepo.save(entity);
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        materialHistoryRepo.deleteById(entityId);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Override
    public List<MaterialHistory> findByMaterialId(Integer materialId) {
        return materialHistoryRepo.findByMaterialId(materialId);
    }

    @Override
    public List<MaterialHistory> findByFieldName(String fieldName) {
        return materialHistoryRepo.findByFieldName(fieldName);
    }

    @Override
    public List<MaterialHistory> save(Map<String, Object[]> logChanges, String title, Integer materialId) {
        List<MaterialHistory> materialHistories = new ArrayList<>();
        for (Map.Entry<String, Object[]> entry : logChanges.entrySet()) {
            String field = entry.getKey();
            String oldValue = entry.getValue()[0].toString();
            String newValue = entry.getValue()[1].toString();
            MaterialHistory materialHistory = new MaterialHistory();
            materialHistory.setTitle("Update material");
            materialHistory.setMaterial(new Material(materialId));
            materialHistory.setFieldName(field);
            materialHistory.setOldValue(oldValue);
            materialHistory.setNewValue(newValue);
            materialHistories.add(this.save(materialHistory));
        }
        return materialHistories;
    }
}