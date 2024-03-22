package com.flowiee.pms.service.product;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.entity.product.ProductHistory;

import java.util.List;

public interface ProductHistoryService extends BaseService<ProductHistory> {
    List<ProductHistory> findByProduct(Integer productId);

    List<ProductHistory> findPriceChange(Integer productDetailId);
}