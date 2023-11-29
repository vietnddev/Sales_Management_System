package com.flowiee.app.service.impl;

import com.flowiee.app.entity.VoucherApply;
import com.flowiee.app.repository.VoucherApplyRepository;
import com.flowiee.app.service.VoucherApplyService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherApplyServiceImpl implements VoucherApplyService {
    @Autowired
    private VoucherApplyRepository voucherApplyRepository;

    @Override
    public List<VoucherApply> findAll() {
        return voucherApplyRepository.findAll();
    }

    @Override
    public List<VoucherApply> findByVoucherId(Integer voucherId) {
        return voucherApplyRepository.findByVoucherId(voucherId);
    }

    @Override
    public VoucherApply findById(Integer id) {
        return voucherApplyRepository.findById(id).orElse(null);
    }

    @Override
    public String save(VoucherApply voucherApply) {
        voucherApplyRepository.save(voucherApply);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(VoucherApply voucherApply, Integer id) {
        if (this.findById(id) != null) {
            voucherApply.setId(id);
            voucherApplyRepository.save(voucherApply);
        }
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (this.findById(entityId) != null) {
            voucherApplyRepository.deleteById(entityId);
        }
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}