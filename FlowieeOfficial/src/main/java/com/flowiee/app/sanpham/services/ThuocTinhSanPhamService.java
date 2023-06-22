package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.ThuocTinhSanPham;

import java.util.List;
import java.util.Optional;

public interface ThuocTinhSanPhamService {

    List<ThuocTinhSanPham> getAllAttributes(int productVariantID);

    void saveAttribute(ThuocTinhSanPham productAttribute);

    ThuocTinhSanPham findById(int attributeID);
    
    void deleteAttribute(int attributeID);
}