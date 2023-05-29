package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.SanPham;

import java.util.List;
import java.util.Optional;

public interface BienTheSanPhamService {

    List<BienTheSanPham> getListVariantOfProduct(String loaiBienThe, int sanPhamId);

    void insertVariant(BienTheSanPham productVariant);
    
    Optional<BienTheSanPham> getByVariantID(int productVariantID);

    void deteleVariant(int productVariantID);

}