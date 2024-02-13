package com.flowiee.app.service.impl;

import com.flowiee.app.dto.VoucherInfoDTO;
import com.flowiee.app.entity.VoucherTicket;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.repository.VoucherTicketlRepository;
import com.flowiee.app.service.VoucherService;
import com.flowiee.app.service.VoucherTicketService;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VoucherTicketServiceImpl implements VoucherTicketService {
    @Autowired
    private VoucherTicketlRepository voucherTicketlRepo;
    @Autowired
    private VoucherService voucherInfoService;

    @Override
    public List<VoucherTicket> findAll() {
        return voucherTicketlRepo.findAll();
    }

    @Override
    public List<VoucherTicket> findByVoucherInfoId(Integer voucherId) {
        return voucherTicketlRepo.findByVoucherId(voucherId);
    }

    @Override
    public VoucherTicket findById(Integer voucherDetailId) {
        return voucherTicketlRepo.findById(voucherDetailId).orElse(null);
    }

    @Transactional
    @Override
    public VoucherTicket save(VoucherTicket voucherTicket) {
        if (voucherTicket == null) {
            throw new BadRequestException();
        }
        if (this.findByCode(voucherTicket.getCode()) == null) {
        	return voucherTicketlRepo.save(voucherTicket);
        } else {
        	throw new AppException();
        }
    }

    @Transactional
    @Override
    public VoucherTicket update(VoucherTicket voucherTicket, Integer voucherDetailId) {
        if (voucherTicket == null || voucherDetailId == null || voucherDetailId <= 0) {
            throw new BadRequestException();
        }
        voucherTicket.setId(voucherDetailId);
        return voucherTicketlRepo.save(voucherTicket);
    }

    @Override
    public String delete(Integer entityId) {
        VoucherTicket voucherTicket = this.findById(entityId);
        if (voucherTicket == null || voucherTicket.isStatus()) {
            throw new AppException();
        }
        voucherTicketlRepo.deleteById(entityId);
        return MessageUtils.DELETE_SUCCESS;
    }

	@Override
	public VoucherTicket findByCode(String code) {
		return voucherTicketlRepo.findByCode(code);
	}

	@Override
	public String checkTicketToUse(String code) {
		String statusTicket = "";
		VoucherTicket ticket = this.findByCode(code);
		if (ticket != null) {
			VoucherInfoDTO voucherInfo = voucherInfoService.findById(ticket.getId());
			if (AppConstants.VOUCHER_STATUS.ACTIVE.name().equals(voucherInfo.getStatus())) {
				statusTicket = AppConstants.VOUCHER_STATUS.ACTIVE.getLabel();
			} else if (AppConstants.VOUCHER_STATUS.INACTIVE.name().equals(voucherInfo.getStatus())) {
				statusTicket = AppConstants.VOUCHER_STATUS.INACTIVE.getLabel();
			}
		} else {
			statusTicket = "Invalid!";
		}
		return statusTicket;
	}
}