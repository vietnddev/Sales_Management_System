package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Supplier;

import java.util.List;

public interface SupplierService extends BaseService<Supplier> {
    List<Supplier> findAll();
}