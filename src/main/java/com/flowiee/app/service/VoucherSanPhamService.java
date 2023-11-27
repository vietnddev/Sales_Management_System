package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.VoucherSanPham;

import java.util.List;

public interface VoucherSanPhamService extends BaseService<VoucherSanPham> {
    List<VoucherSanPham> findByVoucherId(Integer voucherId);
}