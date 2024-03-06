package com.flowiee.sms.service;

import com.flowiee.sms.core.BaseService;
import com.flowiee.sms.entity.Supplier;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SupplierService extends BaseService<Supplier> {
    List<Supplier> findAll();

    Page<Supplier> findAll(Integer pageSize, Integer pageNum);
}