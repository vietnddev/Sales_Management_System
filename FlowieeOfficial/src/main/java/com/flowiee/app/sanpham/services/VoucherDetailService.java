package com.flowiee.app.sanpham.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.sanpham.entity.VoucherDetail;

import java.util.List;

public interface VoucherDetailService extends BaseService<VoucherDetail> {
    List<VoucherDetail> findByVoucherId(Integer voucherId);
}