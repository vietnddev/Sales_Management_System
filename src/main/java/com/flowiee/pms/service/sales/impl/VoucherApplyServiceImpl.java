package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.model.dto.VoucherApplyDTO;
import com.flowiee.pms.entity.sales.VoucherApply;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.sales.VoucherApplyRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.VoucherApplyService;

import com.flowiee.pms.utils.constants.MessageCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VoucherApplyServiceImpl extends BaseService implements VoucherApplyService {
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
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    private List<VoucherApplyDTO> extractDataQuery(List<Object[]> objects) {
        List<VoucherApplyDTO> dataResponse = new ArrayList<>();
        for (Object[] data : objects) {
            dataResponse.add(VoucherApplyDTO.builder()
                    .voucherApplyId(Integer.parseInt(String.valueOf(data[0])))
                    .voucherInfoId(Integer.parseInt(String.valueOf(data[1])))
                    .voucherInfoTitle(String.valueOf(data[2]))
                    .productId(Integer.parseInt(String.valueOf(data[3])))
                    .productName(String.valueOf(data[4]))
                    .appliedAt((String.valueOf(data[5])).substring(0, 10))
                    .appliedBy(Integer.parseInt(String.valueOf(data[6])))
                    .build());
        }
        return dataResponse;
    }
}