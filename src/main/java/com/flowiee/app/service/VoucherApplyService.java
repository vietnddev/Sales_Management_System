package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.VoucherApply;

import java.util.List;

public interface VoucherApplyService extends BaseService<VoucherApply> {
    List<VoucherApply> findByVoucherId(Integer voucherId);
}