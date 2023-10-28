package com.flowiee.app.sanpham.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.sanpham.entity.ProductVariantTemp;

import java.util.List;

public interface ProductVariantTempService extends BaseService<ProductVariantTemp> {
    List<ProductVariantTemp> getListVariantOfProduct(int sanPhamId);

    Double getGiaBan(int id);

    String updateSoLuong(Integer soLuong, Integer id);

    List<ProductVariantTemp> findByImportId(Integer importId);
}