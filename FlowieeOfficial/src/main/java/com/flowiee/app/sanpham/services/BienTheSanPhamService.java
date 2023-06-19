package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.model.BienTheSanPhamResponse;

import java.util.List;

public interface BienTheSanPhamService {

    List<BienTheSanPham> findAll();

    List<BienTheSanPham> getListVariantOfProduct(int sanPhamId);

    Double getGiaBan(int id);

    String save(BienTheSanPham bienTheSanPham);

    String update(BienTheSanPham bienTheSanPham, int id);
    
    BienTheSanPham findById(int id);

    String detele(int id);

    List<BienTheSanPhamResponse> convertToBienTheSanPhamResponse(List<BienTheSanPham> listBienTheSanPham);
}