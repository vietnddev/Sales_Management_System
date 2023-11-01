package com.flowiee.app.product.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.product.entity.VoucherSanPham;

import java.util.List;

public interface VoucherSanPhamService extends BaseService<VoucherSanPham> {
    List<VoucherSanPham> findByVoucherId(Integer voucherId);
}