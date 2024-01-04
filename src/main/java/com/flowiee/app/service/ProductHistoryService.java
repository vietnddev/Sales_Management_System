package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.ProductHistory;

import java.util.List;

public interface ProductHistoryService extends BaseService<ProductHistory> {
    List<ProductHistory> findAll();

    List<ProductHistory> findByProduct(Integer productId);

    List<ProductHistory> findByProductVariant(Integer productVariantId);

    List<ProductHistory> findByProductAttribute(Integer productAttributeId);

    List<ProductHistory> findByProductPrice(Integer productPriceId);
}