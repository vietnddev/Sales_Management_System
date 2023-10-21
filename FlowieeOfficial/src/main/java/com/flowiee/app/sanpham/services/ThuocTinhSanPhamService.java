package com.flowiee.app.sanpham.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.sanpham.entity.ThuocTinhSanPham;

import java.util.List;

public interface ThuocTinhSanPhamService extends BaseService<ThuocTinhSanPham> {
    List<ThuocTinhSanPham> getAllAttributes(int productVariantID);
}