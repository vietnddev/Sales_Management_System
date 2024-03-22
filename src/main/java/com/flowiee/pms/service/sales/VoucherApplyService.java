package com.flowiee.pms.service.sales;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.model.dto.VoucherApplyDTO;
import com.flowiee.pms.entity.sales.VoucherApply;

import java.util.List;

public interface VoucherApplyService extends BaseService<VoucherApply> {
    List<VoucherApplyDTO> findAll(Integer voucherInfoId , Integer productId);

    List<VoucherApplyDTO> findByProductId(Integer productId);

    List<VoucherApply> findByVoucherId(Integer voucherId);
}