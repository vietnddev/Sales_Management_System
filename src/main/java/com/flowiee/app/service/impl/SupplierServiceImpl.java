package com.flowiee.app.service.impl;

import com.flowiee.app.entity.Supplier;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.repository.SupplierRepository;
import com.flowiee.app.service.SupplierService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepo;

    @Override
    public List<Supplier> findAll() {
        return supplierRepo.findAll();
    }

    @Override
    public Page<Supplier> findAll(Integer pageSize, Integer pageNum) {
        Pageable pageable;
        if (pageSize == null || pageNum == null) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("name").ascending());
        }
        return supplierRepo.findAll(pageable);
    }

    @Override
    public Supplier findById(Integer entityId) {
        return supplierRepo.findById(entityId).orElse(null);
    }

    @Override
    public Supplier save(Supplier entity) {
        return supplierRepo.save(entity);
    }

    @Override
    public Supplier update(Supplier entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return supplierRepo.save(entity);
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        supplierRepo.deleteById(entityId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}