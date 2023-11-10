package com.flowiee.app.service.product;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.product.VoucherSanPham;

import java.util.List;

public interface VoucherSanPhamService extends BaseService<VoucherSanPham> {
    List<VoucherSanPham> findByVoucherId(Integer voucherId);
}