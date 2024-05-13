package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.Material;
import com.flowiee.pms.entity.product.MaterialHistory;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.product.MaterialRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.MaterialHistoryService;
import com.flowiee.pms.service.product.MaterialService;

import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl extends BaseService implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialHistoryService materialHistoryService;

    @Override
    public List<Material> findAll() {
        return this.findAll(-1, -1, null, null, null, null, null, null).getContent();
    }

    @Override
    public Page<Material> findAll(int pageSize, int pageNum, Integer supplierId, Integer unitId, String code, String name, String location, String status) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("name").ascending());
        }
        return materialRepository.findAll(supplierId, unitId, code, name, location, status, pageable);
    }

    @Override
    public Optional<Material> findById(Integer entityId) {
        return materialRepository.findById(entityId);
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
        Optional<Material> materialBefore = this.findById(entityId);
        if (materialBefore.isEmpty()) {
            throw new BadRequestException();
        }
        materialBefore.get().compareTo(entity).forEach((key, value) -> {
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
        if (this.findById(entityId).isEmpty()) {
            throw new BadRequestException("Material not found!");
        }
        materialRepository.deleteById(entityId);
        return MessageUtils.DELETE_SUCCESS;
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