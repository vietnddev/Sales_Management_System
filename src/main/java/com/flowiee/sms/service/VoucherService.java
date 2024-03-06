package com.flowiee.sms.service;

import com.flowiee.sms.entity.VoucherInfo;
import com.flowiee.sms.model.dto.VoucherInfoDTO;
import com.flowiee.sms.entity.VoucherTicket;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface VoucherService {
    Page<VoucherInfoDTO> findAllVouchers(String status, Date startTime, Date endTime, String title, Integer pageSize, Integer pageNum);

    Page<VoucherTicket> findTickets(Integer voucherId, Integer pageSize, Integer pageNum);

    List<VoucherTicket> findTickets(Integer voucherId);

    List<VoucherInfoDTO> findAllVouchers(List<Integer> voucherIds, String status);

    VoucherInfoDTO findVoucherDetail(Integer voucherId);

    VoucherTicket isAvailable(String voucherTicketCode);

    VoucherInfo saveVoucher(VoucherInfoDTO voucherInfo);

    VoucherInfo updateVoucher(VoucherInfo voucherInfo, Integer voucherId);

    VoucherTicket findTicketById(Integer voucherTicketId);

    VoucherTicket findTicketByCode(String code);

    VoucherTicket saveTicket(VoucherTicket voucherTicket);

    VoucherTicket updateTicket(VoucherTicket voucherTicket, Integer ticketId);

    String deteleVoucher(Integer voucherId);

    String deteleTicket(Integer ticketId);

    String checkTicketToUse(String code);
}