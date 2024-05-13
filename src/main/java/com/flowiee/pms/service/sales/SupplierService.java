package com.flowiee.pms.service.sales;

import com.flowiee.pms.service.CrudService;
import com.flowiee.pms.entity.sales.Supplier;
import org.springframework.data.domain.Page;

public interface SupplierService extends CrudService<Supplier> {
    Page<Supplier> findAll(Integer pageSize, Integer pageNum);
}