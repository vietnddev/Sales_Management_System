package com.flowiee.app.service.impl;

import com.flowiee.app.entity.Category;
import com.flowiee.app.entity.CategoryHistory;
import com.flowiee.app.entity.Material;
import com.flowiee.app.entity.MaterialHistory;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.repository.MaterialRepository;
import com.flowiee.app.service.MaterialHistoryService;
import com.flowiee.app.service.MaterialService;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialHistoryService materialHistoryService;

    @Override
    public Page<Material> findAll(Integer pageSize, Integer pageNum) {
        Pageable pageable;
        if (pageSize == null || pageNum == null) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("name").ascending());
        }
        return materialRepository.findAll(pageable);
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
    public Material save(Material entity) {
        return materialRepository.save(entity);
    }

    @Override
    public Material update(Material entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            throw new BadRequestException();
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
        return materialRepository.save(entity);
    }

    @Override
    public String delete(Integer entityId) {
        if (this.findById(entityId) == null) {
            throw new BadRequestException();
        }
        materialRepository.deleteById(entityId);
        return MessageUtils.DELETE_SUCCESS;
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

    @Transactional
    @Override
    public void updateQuantity(Integer quantity, Integer materialId, String type) {
        if ("I".equals(type)) {
            materialRepository.updateQuantityIncrease(quantity, materialId);
        } else if ("D".equals(type)) {
            materialRepository.updateQuantityDecrease(quantity, materialId);
        }
    }
}