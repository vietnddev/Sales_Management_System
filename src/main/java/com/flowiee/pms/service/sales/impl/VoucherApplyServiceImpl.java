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
    VoucherApplyRepository mvVoucherApplyRepository;

    @Override
    public List<VoucherApplyDTO> findAll(Long voucherInfoId , Long productId) {
        return this.extractDataQuery(mvVoucherApplyRepository.findAll((Long) null));
    }

    @Override
    public List<VoucherApplyDTO> findByProductId(Long productId) {
        return this.extractDataQuery(mvVoucherApplyRepository.findAll(productId));
    }

    @Override
    public List<VoucherApply> findAll() {
        return mvVoucherApplyRepository.findAll();
    }

    @Override
    public List<VoucherApply> findByVoucherId(Long voucherId) {
        return mvVoucherApplyRepository.findByVoucherId(voucherId);
    }

    @Override
    public Optional<VoucherApply> findById(Long id) {
        return mvVoucherApplyRepository.findById(id);
    }

    @Override
    public VoucherApply save(VoucherApply voucherApply) {
        return mvVoucherApplyRepository.save(voucherApply);
    }

    @Override
    public VoucherApply update(VoucherApply voucherApply, Long id) {
        if (this.findById(id).isEmpty()) {
            throw new BadRequestException();
        }
        voucherApply.setId(id);
        return mvVoucherApplyRepository.save(voucherApply);
    }

    @Override
    public String delete(Long entityId) {
        if (this.findById(entityId).isEmpty()) {
            mvVoucherApplyRepository.deleteById(entityId);
        }
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    private List<VoucherApplyDTO> extractDataQuery(List<Object[]> objects) {
        List<VoucherApplyDTO> dataResponse = new ArrayList<>();
        for (Object[] data : objects) {
            dataResponse.add(VoucherApplyDTO.builder()
                    .voucherApplyId(Long.parseLong(String.valueOf(data[0])))
                    .voucherInfoId(Long.parseLong(String.valueOf(data[1])))
                    .voucherInfoTitle(String.valueOf(data[2]))
                    .productId(Long.parseLong(String.valueOf(data[3])))
                    .productName(String.valueOf(data[4]))
                    .appliedAt((String.valueOf(data[5])).substring(0, 10))
                    .appliedBy(Long.parseLong(String.valueOf(data[6])))
                    .build());
        }
        return dataResponse;
    }
}