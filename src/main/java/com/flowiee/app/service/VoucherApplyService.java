package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.model.dto.VoucherApplyDTO;
import com.flowiee.app.entity.VoucherApply;

import java.util.List;

public interface VoucherApplyService extends BaseService<VoucherApply> {
    List<VoucherApply> findAll();

    List<VoucherApplyDTO> findAll(Integer voucherInfoId , Integer productId);

    List<VoucherApplyDTO> findByProductId(Integer productId);

    VoucherApplyDTO findOneByProductId(Integer productId);

    List<VoucherApply> findByVoucherId(Integer voucherId);
}