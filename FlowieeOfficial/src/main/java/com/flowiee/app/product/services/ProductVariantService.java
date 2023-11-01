package com.flowiee.app.product.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.product.entity.ProductVariant;

import java.util.List;

public interface ProductVariantService extends BaseService<ProductVariant> {
    List<ProductVariant> getListVariantOfProduct(int sanPhamId);

    Double getGiaBan(int id);

    String updateSoLuong(Integer soLuong, Integer id);

    List<ProductVariant> findByImportId(Integer importId);
}