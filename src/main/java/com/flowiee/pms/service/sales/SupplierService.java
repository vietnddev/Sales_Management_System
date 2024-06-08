package com.flowiee.pms.service.sales;

import com.flowiee.pms.service.BaseCurdService;
import com.flowiee.pms.entity.sales.Supplier;
import org.springframework.data.domain.Page;

public interface SupplierService extends BaseCurdService<Supplier> {
    Page<Supplier> findAll(Integer pageSize, Integer pageNum);
}