package com.flowiee.pms.service.product;

import com.flowiee.pms.service.BaseCurdService;
import com.flowiee.pms.entity.product.ProductAttribute;
import org.springframework.data.domain.Page;

public interface ProductAttributeService extends BaseCurdService<ProductAttribute> {
    Page<ProductAttribute> findAll(int pageSize, int pageNum, Integer pProductDetailId);
}