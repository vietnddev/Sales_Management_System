package com.flowiee.app.products.services;

import com.flowiee.app.products.entity.ThuocTinhSanPham;

import java.util.List;
import java.util.Optional;

public interface ThuocTinhSanPhamService {

    List<ThuocTinhSanPham> getAllAttributes(int productVariantID);

    void saveAttribute(ThuocTinhSanPham productAttribute);

    Optional<ThuocTinhSanPham> getByAttributeID(int attributeID);
    
    void deleteAttribute(int attributeID);
}