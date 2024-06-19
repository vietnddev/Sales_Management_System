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
    ModelMapper             modelMapper;
    VoucherService          voucherService;
    VoucherTicketRepository voucherTicketRepo;

    @Override
    public List<VoucherTicket> findAll() {
        return this.findAll(-1, -1, null).getContent();
    }

    @Override
    public Page<VoucherTicket> findAll(int pageSize, int pageNum, Integer voucherId) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("id").ascending());
        }
        return voucherTicketRepo.findByVoucherId(voucherId, pageable);
    }

    @Override
    public Optional<VoucherTicket> findById(Integer voucherTicketId) {
        return voucherTicketRepo.findById(voucherTicketId);
    }

    @Transactional
    @Override
    public VoucherTicket save(VoucherTicket voucherTicket) {
        if (voucherTicket == null) {
            throw new BadRequestException();
        }
        if (this.findTicketByCode(voucherTicket.getCode()) == null) {
            return voucherTicketRepo.save(voucherTicket);
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
        return voucherTicketRepo.save(voucherTicket);
    }

    @Override
    public String delete(Integer ticketId) {
        Optional<VoucherTicket> voucherTicket = this.findById(ticketId);
        if (voucherTicket.isEmpty() || voucherTicket.get().isUsed()) {
            throw new AppException();
        }
        voucherTicketRepo.deleteById(ticketId);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Override
    public List<VoucherTicket> findByVoucherId(Integer voucherId) {
        return voucherTicketRepo.findByVoucherId(voucherId, Pageable.unpaged()).getContent();
    }

    @Override
    public VoucherTicket findByCode(String code) {
        return voucherTicketRepo.findByCode(code);
    }

    @Override
    public VoucherTicketDTO isAvailable(String voucherTicketCode) {
        VoucherTicket voucherTicket = voucherTicketRepo.findByCode(voucherTicketCode);
        if (voucherTicket == null) {
            VoucherTicketDTO voucherTicketDTO = new VoucherTicketDTO();
            voucherTicketDTO.setAvailable("N");
            return voucherTicketDTO;
        }
        VoucherTicketDTO voucherTicketDTO = modelMapper.map(voucherTicket, VoucherTicketDTO.class);
        Optional<VoucherInfoDTO> voucherInfoDTO = voucherService.findById(voucherTicketDTO.getVoucherInfo().getId());
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
        return voucherTicketRepo.findByCode(code);
    }

    @Override
    public String checkTicketToUse(String code) {
        String statusTicket = "";
        VoucherTicket ticket = voucherTicketRepo.findByCode(code);
        if (ticket != null) {
            Optional<VoucherInfoDTO> voucherInfo = voucherService.findById(ticket.getId());
            if (voucherInfo.isEmpty()) {
                throw new AppException();
            }
            if (VoucherStatus.A.name().equals(voucherInfo.get().getStatus())) {
                statusTicket = VoucherStatus.A.getLabel();
            } else if (VoucherStatus.I.name().equals(voucherInfo.get().getStatus())) {
                statusTicket = VoucherStatus.I.getLabel();
            }
        } else {
            statusTicket = "Invalid!";
        }
        return statusTicket;
    }
}