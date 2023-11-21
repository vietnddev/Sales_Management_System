package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.Supplier;
import com.flowiee.app.repository.SupplierRepository;
import com.flowiee.app.service.product.SupplierService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier findById(Integer entityId) {
        return supplierRepository.findById(entityId).get();
    }

    @Override
    public String save(Supplier entity) {
        supplierRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(Supplier entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        supplierRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        supplierRepository.deleteById(entityId);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }
}