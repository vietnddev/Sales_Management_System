package com.flowiee.sms.service;

import com.flowiee.sms.core.BaseService;
import com.flowiee.sms.model.dto.VoucherApplyDTO;
import com.flowiee.sms.entity.VoucherApply;

import java.util.List;

public interface VoucherApplyService extends BaseService<VoucherApply> {
    List<VoucherApply> findAll();

    List<VoucherApplyDTO> findAll(Integer voucherInfoId , Integer productId);

    List<VoucherApplyDTO> findByProductId(Integer productId);

    VoucherApplyDTO findOneByProductId(Integer productId);

    List<VoucherApply> findByVoucherId(Integer voucherId);
}