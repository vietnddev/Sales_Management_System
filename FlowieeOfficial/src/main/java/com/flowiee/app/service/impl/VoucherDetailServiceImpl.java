package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.product.VoucherDetail;
import com.flowiee.app.repository.product.VoucherDetailRepository;
import com.flowiee.app.service.product.VoucherDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherDetailServiceImpl implements VoucherDetailService {
    @Autowired
    private VoucherDetailRepository voucherDetailRepository;

    @Override
    public List<VoucherDetail> findAll() {
        return voucherDetailRepository.findAll();
    }

    @Override
    public List<VoucherDetail> findByVoucherId(Integer voucherId) {
        return voucherDetailRepository.findByVoucherId(voucherId);
    }

    @Override
    public VoucherDetail findById(Integer voucherDetailId) {
        return voucherDetailRepository.findById(voucherDetailId).orElse(null);
    }

    @Override
    public String save(VoucherDetail voucherDetail) {
        if (voucherDetail == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        voucherDetailRepository.save(voucherDetail);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(VoucherDetail voucherDetail, Integer voucherDetailId) {
        if (voucherDetail == null || voucherDetailId == null || voucherDetailId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        voucherDetail.setId(voucherDetailId);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        VoucherDetail voucherDetail = this.findById(entityId);
        if (voucherDetail == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        voucherDetailRepository.deleteById(entityId);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }
}