package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.Supplier;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.repository.sales.SupplierRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.SupplierService;

import com.flowiee.pms.utils.LogUtils;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.utils.constants.MasterObject;
import com.flowiee.pms.utils.constants.MessageCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SupplierServiceImpl extends BaseService implements SupplierService {
    SupplierRepository supplierRepo;

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
        Optional<Supplier> supplierOptional = this.findById(entityId);
        if (supplierOptional.isEmpty()) {
            throw new ResourceNotFoundException("Supplier not found!");
        }
        Supplier supplierBefore =  ObjectUtils.clone(supplierOptional.get());
        entity.setId(entityId);
        Supplier supplierUpdated = supplierRepo.save(entity);

        Map<String, Object[]> logChanges = LogUtils.logChanges(supplierBefore, supplierUpdated);
        systemLogService.writeLogUpdate(MODULE.SALES, ACTION.PRO_SUP_U, MasterObject.Supplier, "Cập nhật thông tin nhà cung cấp: " + supplierUpdated.getName(), logChanges);

        return supplierUpdated;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        supplierRepo.deleteById(entityId);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }
}