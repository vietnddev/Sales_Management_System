package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.MaterialHistory;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.product.MaterialHistoryRepository;
import com.flowiee.pms.service.product.MaterialHistoryService;

import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialHistoryServiceImpl implements MaterialHistoryService {
    @Autowired
    private MaterialHistoryRepository materialHistoryRepo;

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