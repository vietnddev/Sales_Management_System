package com.flowiee.sms.service.impl;

import com.flowiee.sms.entity.Supplier;
import com.flowiee.sms.core.exception.BadRequestException;
import com.flowiee.sms.repository.SupplierRepository;
import com.flowiee.sms.service.SupplierService;

import com.flowiee.sms.utils.MessageUtils;
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
    public Supplier save(Supplier supplier) {
        supplier.setStatus("A");
        return supplierRepo.save(supplier);
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
            throw new BadRequestException();
        }
        supplierRepo.deleteById(entityId);
        return MessageUtils.DELETE_SUCCESS;
    }
}