package com.flowiee.app.service.product;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.ProductAttribute;

import java.util.List;

public interface ProductAttributeService extends BaseService<ProductAttribute> {
    List<ProductAttribute> getAllAttributes(int productVariantID);
}