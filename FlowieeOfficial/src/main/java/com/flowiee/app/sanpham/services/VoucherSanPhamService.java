package com.flowiee.app.sanpham.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.sanpham.entity.VoucherSanPham;

import java.util.List;

public interface VoucherSanPhamService extends BaseService<VoucherSanPham> {
    List<VoucherSanPham> findByVoucherId(Integer voucherId);
}