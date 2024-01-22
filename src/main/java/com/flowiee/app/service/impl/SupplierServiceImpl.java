package com.flowiee.app.service.impl;

import com.flowiee.app.entity.Supplier;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.repository.SupplierRepository;
import com.flowiee.app.service.SupplierService;

import com.flowiee.app.utils.AppConstants;
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
        return supplierRepository.findById(entityId).orElse(null);
    }

    @Override
    public Supplier save(Supplier entity) {
        return supplierRepository.save(entity);
    }

    @Override
    public Supplier update(Supplier entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return supplierRepository.save(entity);
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        supplierRepository.deleteById(entityId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}