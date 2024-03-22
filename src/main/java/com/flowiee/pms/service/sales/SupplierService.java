package com.flowiee.pms.service.sales;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.entity.sales.Supplier;
import org.springframework.data.domain.Page;

public interface SupplierService extends BaseService<Supplier> {
    Page<Supplier> findAll(Integer pageSize, Integer pageNum);
}