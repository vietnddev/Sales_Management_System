package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.VoucherDetail;

import java.util.List;

public interface VoucherDetailService extends BaseService<VoucherDetail> {
    List<VoucherDetail> findByVoucherId(Integer voucherId);
}