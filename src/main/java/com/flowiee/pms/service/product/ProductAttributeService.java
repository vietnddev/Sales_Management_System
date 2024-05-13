package com.flowiee.pms.service.product;

import com.flowiee.pms.service.CrudService;
import com.flowiee.pms.entity.product.ProductAttribute;
import org.springframework.data.domain.Page;

public interface ProductAttributeService extends CrudService<ProductAttribute> {
    Page<ProductAttribute> findAll(int pageSize, int pageNum, Integer pProductDetailId);
}