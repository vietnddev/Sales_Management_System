package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.Supplier;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.sales.SupplierRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.SupplierService;

import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl extends BaseService implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepo;

    @Override
    public List<Supplier> findAll() {
        return this.findAll(-1, -1).getContent();
    }

    @Override
    public Page<Supplier> findAll(Integer pageSize, Integer pageNum) {
        Pageable pageable = Pageable.unpaged();
        if ((pageSize != null && pageSize >= 0) || (pageNum != null && pageNum >= 0)) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("name").ascending());
        }
        return supplierRepo.findAll(pageable);
    }

    @Override
    public Optional<Supplier> findById(Integer entityId) {
        return supplierRepo.findById(entityId);
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