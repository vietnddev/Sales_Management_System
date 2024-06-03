package com.flowiee.pms.service.product;

import com.flowiee.pms.entity.product.ProductCombo;
import com.flowiee.pms.service.BaseCurd;
import org.springframework.data.domain.Page;

public interface ProductComboService extends BaseCurd<ProductCombo> {
    Page<ProductCombo> findAll(int pageSize, int pageNum);
}