package com.flowiee.app.product.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.product.entity.ProductAttribute;

import java.util.List;

public interface ProductAttributeService extends BaseService<ProductAttribute> {
    List<ProductAttribute> getAllAttributes(int productVariantID);
}