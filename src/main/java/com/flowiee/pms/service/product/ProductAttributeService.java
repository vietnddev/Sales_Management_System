package com.flowiee.pms.service.product;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.entity.product.ProductAttribute;
import org.springframework.data.domain.Page;

public interface ProductAttributeService extends BaseService<ProductAttribute> {
    Page<ProductAttribute> findAll(int pageSize, int pageNum, Integer pProductDetailId);
}