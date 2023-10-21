package com.flowiee.app.sanpham.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.sanpham.entity.SanPham;

import java.util.List;

public interface SanPhamService extends BaseService<SanPham> {
    byte[] exportData(List<Integer> listSanPhamId);
}