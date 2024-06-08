package com.flowiee.pms.service.product;

import com.flowiee.pms.service.BaseCurdService;
import com.flowiee.pms.entity.product.ProductHistory;

import java.util.List;
import java.util.Map;

public interface ProductHistoryService extends BaseCurdService<ProductHistory> {
    List<ProductHistory> findByProduct(Integer productId);

    List<ProductHistory> findPriceChange(Integer productDetailId);

    List<ProductHistory> save(Map<String, Object[]> logChanges, String title, Integer productBaseId, Integer productVariantId, Integer productAttributeId);
}