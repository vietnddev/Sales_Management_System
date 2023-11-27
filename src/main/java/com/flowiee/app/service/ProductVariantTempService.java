package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.ProductVariantTemp;

import java.util.List;

public interface ProductVariantTempService extends BaseService<ProductVariantTemp> {
    String updateSoLuong(Integer soLuong, Integer id);

    List<ProductVariantTemp> findByImportId(Integer importId);
    
    ProductVariantTemp findProductVariantInGoodsImport(Integer importId, Integer productVariantId);
}