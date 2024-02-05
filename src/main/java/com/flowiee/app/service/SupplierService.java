package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Supplier;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SupplierService extends BaseService<Supplier> {
    List<Supplier> findAll();

    Page<Supplier> findAll(int pageSize, int pageNum);
}