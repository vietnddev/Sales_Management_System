package com.flowiee.app.service.impl;

import com.flowiee.app.dto.VoucherApplyDTO;
import com.flowiee.app.entity.VoucherApply;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.repository.VoucherApplyRepository;
import com.flowiee.app.service.VoucherApplyService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherApplyServiceImpl implements VoucherApplyService {
    @Autowired
    private VoucherApplyRepository voucherApplyRepository;

    @Override
    public List<VoucherApplyDTO> findAll(Integer voucherInfoId , Integer productId) {
        return this.extractDataQuery(voucherApplyRepository.findAll((Integer) null));
    }

    @Override
    public List<VoucherApplyDTO> findByProductId(Integer productId) {
        return this.extractDataQuery(voucherApplyRepository.findAll(productId));
    }

    @Override
    public VoucherApplyDTO findOneByProductId(Integer productId) {
        return this.extractDataQuery(voucherApplyRepository.findAll(productId)).get(0);
    }

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
    public VoucherApply save(VoucherApply voucherApply) {
        return voucherApplyRepository.save(voucherApply);
    }

    @Override
    public VoucherApply update(VoucherApply voucherApply, Integer id) {
        if (this.findById(id) == null) {
            throw new BadRequestException();
        }
        voucherApply.setId(id);
        return voucherApplyRepository.save(voucherApply);
    }

    @Override
    public String delete(Integer entityId) {
        if (this.findById(entityId) != null) {
            voucherApplyRepository.deleteById(entityId);
        }
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    private List<VoucherApplyDTO> extractDataQuery(List<Object[]> objects) {
        List<VoucherApplyDTO> dataResponse = new ArrayList<>();
        for (Object[] data : objects) {
            VoucherApplyDTO voucherApplyDTO = new VoucherApplyDTO();
            voucherApplyDTO.setVoucherApplyId(Integer.parseInt(String.valueOf(data[0])));
            voucherApplyDTO.setVoucherInfoId(Integer.parseInt(String.valueOf(data[1])));
            voucherApplyDTO.setVoucherInfoTitle(String.valueOf(data[2]));
            voucherApplyDTO.setProductId(Integer.parseInt(String.valueOf(data[3])));
            voucherApplyDTO.setProductName(String.valueOf(data[4]));
            voucherApplyDTO.setAppliedAt((String.valueOf(data[5])).substring(0, 10));
            voucherApplyDTO.setAppliedBy(Integer.parseInt(String.valueOf(data[6])));
            dataResponse.add(voucherApplyDTO);
        }
        return dataResponse;
    }
}