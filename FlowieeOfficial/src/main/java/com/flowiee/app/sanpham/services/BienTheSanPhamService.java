package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.SanPham;

import java.util.List;
import java.util.Optional;

public interface BienTheSanPhamService {

    List<BienTheSanPham> findAll();

    List<BienTheSanPham> getListVariantOfProduct(String loaiBienThe, int sanPhamId);

    Double getGiaBan(int id);

    void save(BienTheSanPham bienTheSanPham);

    void update(BienTheSanPham bienTheSanPham, int id);
    
    BienTheSanPham findById(int productVariantID);

    void detele(int productVariantID);

}