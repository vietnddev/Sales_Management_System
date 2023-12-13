package com.flowiee.app.service.impl;

import com.flowiee.app.dto.VoucherInfoDTO;
import com.flowiee.app.entity.VoucherTicket;
import com.flowiee.app.repository.VoucherTicketlRepository;
import com.flowiee.app.service.VoucherService;
import com.flowiee.app.service.VoucherTicketService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherTicketServiceImpl implements VoucherTicketService {
    @Autowired
    private VoucherTicketlRepository voucherTicketlRepository;
    @Autowired
    private VoucherService voucherInfoService;

    @Override
    public List<VoucherTicket> findAll() {
        return voucherTicketlRepository.findAll();
    }

    @Override
    public List<VoucherTicket> findByVoucherInfoId(Integer voucherId) {
        return voucherTicketlRepository.findByVoucherId(voucherId);
    }

    @Override
    public VoucherTicket findById(Integer voucherDetailId) {
        return voucherTicketlRepository.findById(voucherDetailId).orElse(null);
    }

    @Override
    public String save(VoucherTicket voucherTicket) {
        if (voucherTicket == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        if (this.findByCode(voucherTicket.getCode()) == null) {
        	voucherTicketlRepository.save(voucherTicket);
        	return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } else {
        	return AppConstants.SERVICE_RESPONSE_FAIL;	
        }
    }

    @Override
    public String update(VoucherTicket voucherTicket, Integer voucherDetailId) {
        if (voucherTicket == null || voucherDetailId == null || voucherDetailId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        voucherTicket.setId(voucherDetailId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        VoucherTicket voucherTicket = this.findById(entityId);
        if (voucherTicket == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        voucherTicketlRepository.deleteById(entityId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

	@Override
	public VoucherTicket findByCode(String code) {
		return voucherTicketlRepository.findByCode(code);
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