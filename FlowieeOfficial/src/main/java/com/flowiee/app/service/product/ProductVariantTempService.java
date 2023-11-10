package com.flowiee.app.service.product;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.product.ProductVariantTemp;

import java.util.List;

public interface ProductVariantTempService extends BaseService<ProductVariantTemp> {
    List<ProductVariantTemp> getListVariantOfProduct(int sanPhamId);

    Double getGiaBan(int id);

    String updateSoLuong(Integer soLuong, Integer id);

    List<ProductVariantTemp> findByImportId(Integer importId);
}