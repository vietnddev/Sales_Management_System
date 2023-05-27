package com.flowiee.app.products.services;

import com.flowiee.app.products.entity.BienTheSanPham;

import java.util.List;
import java.util.Optional;

public interface BienTheSanPhamService {

    List<BienTheSanPham> getListVariantOfProduct(String name, int productID);

    void insertVariant(BienTheSanPham productVariant);
    
    Optional<BienTheSanPham> getByVariantID(int productVariantID);

    void deteleVariant(int productVariantID);

}