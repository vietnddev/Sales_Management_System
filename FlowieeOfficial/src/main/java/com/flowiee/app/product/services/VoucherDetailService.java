package com.flowiee.app.product.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.product.entity.VoucherDetail;

import java.util.List;

public interface VoucherDetailService extends BaseService<VoucherDetail> {
    List<VoucherDetail> findByVoucherId(Integer voucherId);
}