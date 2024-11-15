package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.VoucherTicket;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.dto.VoucherInfoDTO;
import com.flowiee.pms.model.dto.VoucherTicketDTO;
import com.flowiee.pms.repository.sales.VoucherTicketRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.VoucherService;
import com.flowiee.pms.service.sales.VoucherTicketService;
import com.flowiee.pms.utils.constants.MessageCode;
import com.flowiee.pms.utils.constants.VoucherStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VoucherTicketServiceImpl extends BaseService implements VoucherTicketService {
    ModelMapper             mvModelMapper;
    VoucherService          mvVoucherService;
    VoucherTicketRepository mvVoucherTicketRepository;

    @Override
    public List<VoucherTicket> findAll() {
        return this.findAll(-1, -1, null).getContent();
    }

    @Override
    public Page<VoucherTicket> findAll(int pageSize, int pageNum, Long voucherId) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("id").ascending());
        }
        return mvVoucherTicketRepository.findByVoucherId(voucherId, pageable);
    }

    @Override
    public Optional<VoucherTicket> findById(Long voucherTicketId) {
        return mvVoucherTicketRepository.findById(voucherTicketId);
    }

    @Transactional
    @Override
    public VoucherTicket save(VoucherTicket voucherTicket) {
        if (voucherTicket == null) {
            throw new BadRequestException();
        }
        if (this.findTicketByCode(voucherTicket.getCode()) == null) {
            return mvVoucherTicketRepository.save(voucherTicket);
        } else {
            throw new AppException();
        }
    }

    @Transactional
    @Override
    public VoucherTicket update(VoucherTicket voucherTicket, Long voucherDetailId) {
        if (voucherTicket == null || voucherDetailId == null || voucherDetailId <= 0) {
            throw new BadRequestException();
        }
        voucherTicket.setId(voucherDetailId);
        return mvVoucherTicketRepository.save(voucherTicket);
    }

    @Override
    public String delete(Long ticketId) {
        Optional<VoucherTicket> voucherTicket = this.findById(ticketId);
        if (voucherTicket.isEmpty() || voucherTicket.get().isUsed()) {
            throw new AppException();
        }
        mvVoucherTicketRepository.deleteById(ticketId);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Override
    public List<VoucherTicket> findByVoucherId(Long voucherId) {
        return mvVoucherTicketRepository.findByVoucherId(voucherId, Pageable.unpaged()).getContent();
    }

    @Override
    public VoucherTicket findByCode(String code) {
        return mvVoucherTicketRepository.findByCode(code);
    }

    @Override
    public VoucherTicketDTO isAvailable(String voucherTicketCode) {
        VoucherTicket voucherTicket = mvVoucherTicketRepository.findByCode(voucherTicketCode);
        if (voucherTicket == null) {
//            VoucherTicketDTO voucherTicketDTO = new VoucherTicketDTO();
//            voucherTicketDTO.setAvailable("N");
            return new VoucherTicketDTO("N");
        }
        VoucherTicketDTO voucherTicketDTO = mvModelMapper.map(voucherTicket, VoucherTicketDTO.class);
        Optional<VoucherInfoDTO> voucherInfoDTO = mvVoucherService.findById(voucherTicketDTO.getVoucherInfo().getId());
        if (voucherInfoDTO.isPresent()) {
            LocalDateTime currentTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
            LocalDateTime startTime = voucherInfoDTO.get().getStartTime().withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endTime = voucherInfoDTO.get().getEndTime().withHour(0).withMinute(0).withSecond(0);
            if ((currentTime.isAfter(startTime) || currentTime.isEqual(startTime)) && (currentTime.isBefore(endTime) || currentTime.isEqual(endTime))) {
                voucherTicketDTO.setAvailable("Y");
            } else {
                voucherTicketDTO.setAvailable("N");
            }
        }
        return voucherTicketDTO;
    }

    @Override
    public VoucherTicket findTicketByCode(String code) {
        return mvVoucherTicketRepository.findByCode(code);
    }

    @Override
    public String checkTicketToUse(String code) {
        String statusTicket = "";
        VoucherTicket ticket = mvVoucherTicketRepository.findByCode(code);
        if (ticket != null) {
            Optional<VoucherInfoDTO> voucherInfo = mvVoucherService.findById(ticket.getId());
            if (voucherInfo.isEmpty()) {
                throw new AppException();
            }
            statusTicket = voucherInfo.get().isActiveStatus() ? VoucherStatus.A.getLabel() : VoucherStatus.I.getLabel();
        } else {
            statusTicket = "Invalid!";
        }
        return statusTicket;
    }
}