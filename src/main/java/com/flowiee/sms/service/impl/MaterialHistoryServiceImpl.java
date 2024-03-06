package com.flowiee.sms.service.impl;

import com.flowiee.sms.entity.MaterialHistory;
import com.flowiee.sms.core.exception.BadRequestException;
import com.flowiee.sms.repository.MaterialHistoryRepository;
import com.flowiee.sms.service.MaterialHistoryService;

import com.flowiee.sms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialHistoryServiceImpl implements MaterialHistoryService {
    @Autowired
    private MaterialHistoryRepository materialHistoryRepo;

    @Override
    public List<MaterialHistory> findAll() {
        return materialHistoryRepo.findAll();
    }

    @Override
    public MaterialHistory findById(Integer entityId) {
        return materialHistoryRepo.findById(entityId).get();
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
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public List<MaterialHistory> findByMaterialId(Integer materialId) {
        return materialHistoryRepo.findByMaterialId(materialId);
    }

    @Override
    public List<MaterialHistory> findByFieldName(String fieldName) {
        return materialHistoryRepo.findByFieldName(fieldName);
    }
}