package com.flowiee.pms.service.product;

import com.flowiee.pms.entity.product.ProductCombo;
import com.flowiee.pms.service.CrudService;
import org.springframework.data.domain.Page;

public interface ProductComboService extends CrudService<ProductCombo> {
    Page<ProductCombo> findAll(int pageSize, int pageNum);
}