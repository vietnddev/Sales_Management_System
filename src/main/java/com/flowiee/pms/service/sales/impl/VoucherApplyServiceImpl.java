package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.model.dto.VoucherApplyDTO;
import com.flowiee.pms.entity.sales.VoucherApply;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.sales.VoucherApplyRepository;
import com.flowiee.pms.service.sales.VoucherApplyService;

import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherApplyServiceImpl implements VoucherApplyService {
    @Autowired
    private
    VoucherApplyRepository voucherApplyRepo;

    @Override
    public List<VoucherApplyDTO> findAll(Integer voucherInfoId , Integer productId) {
        return this.extractDataQuery(voucherApplyRepo.findAll((Integer) null));
    }

    @Override
    public List<VoucherApplyDTO> findByProductId(Integer productId) {
        return this.extractDataQuery(voucherApplyRepo.findAll(productId));
    }

    @Override
    public List<VoucherApply> findAll() {
        return voucherApplyRepo.findAll();
    }

    @Override
    public List<VoucherApply> findByVoucherId(Integer voucherId) {
        return voucherApplyRepo.findByVoucherId(voucherId);
    }

    @Override
    public Optional<VoucherApply> findById(Integer id) {
        return voucherApplyRepo.findById(id);
    }

    @Override
    public VoucherApply save(VoucherApply voucherApply) {
        return voucherApplyRepo.save(voucherApply);
    }

    @Override
    public VoucherApply update(VoucherApply voucherApply, Integer id) {
        if (this.findById(id).isEmpty()) {
            throw new BadRequestException();
        }
        voucherApply.setId(id);
        return voucherApplyRepo.save(voucherApply);
    }

    @Override
    public String delete(Integer entityId) {
        if (this.findById(entityId).isEmpty()) {
            voucherApplyRepo.deleteById(entityId);
        }
        return MessageUtils.DELETE_SUCCESS;
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