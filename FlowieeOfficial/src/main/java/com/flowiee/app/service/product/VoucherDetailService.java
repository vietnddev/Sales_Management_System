package com.flowiee.app.service.product;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.product.VoucherDetail;

import java.util.List;

public interface VoucherDetailService extends BaseService<VoucherDetail> {
    List<VoucherDetail> findByVoucherId(Integer voucherId);
}