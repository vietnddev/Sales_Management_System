package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.VoucherApply;
import com.flowiee.pms.entity.sales.VoucherInfo;
import com.flowiee.pms.entity.sales.VoucherTicket;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.utils.constants.*;
import com.flowiee.pms.model.dto.ProductDTO;
import com.flowiee.pms.model.dto.VoucherInfoDTO;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.DataInUseException;
import com.flowiee.pms.repository.sales.VoucherInfoRepository;

import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.VoucherApplyService;
import com.flowiee.pms.service.sales.VoucherService;
import com.flowiee.pms.service.sales.VoucherTicketService;
import com.flowiee.pms.utils.LogUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class VoucherInfoServiceImpl extends BaseService implements VoucherService {
    private final VoucherInfoRepository voucherInfoRepo;
    private final VoucherApplyService   voucherApplyService;
    private final VoucherTicketService  voucherTicketService;
    private final ModelMapper           modelMapper;

    public VoucherInfoServiceImpl(VoucherInfoRepository voucherInfoRepo, VoucherApplyService voucherApplyService, @Lazy VoucherTicketService voucherTicketService, ModelMapper modelMapper) {
        this.voucherInfoRepo = voucherInfoRepo;
        this.voucherApplyService = voucherApplyService;
        this.voucherTicketService = voucherTicketService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<VoucherInfoDTO> findAll() {
        Page<VoucherInfoDTO> voucherInfos = this.findAll(-1, -1, null, null, null, null, null);
        return voucherInfos.getContent();
    }

    @Override
    public Page<VoucherInfoDTO> findAll(int pageSize, int pageNum, List<Integer> pIds, String pTitle, LocalDateTime pStartTime, LocalDateTime pEndTime, String pStatus) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        }
        if (pEndTime == null) {
            pEndTime = LocalDateTime.of(2100, 12, 31, 0, 0);
        }
        if (pStartTime == null) {
            pStartTime = LocalDateTime.of(1900, 1, 1, 0, 0);
        }
        Page<VoucherInfo> pageVoucherInfoDTOs = voucherInfoRepo.findAll(null, pTitle, pStartTime, pEndTime, pStatus, pageable);

        Type listType = new TypeToken<List<VoucherInfoDTO>>() {}.getType();
        List<VoucherInfoDTO> voucherInfoDTOs = modelMapper.map(pageVoucherInfoDTOs.getContent(), listType);

        for (VoucherInfoDTO v : voucherInfoDTOs) {
            v.setStatus(genVoucherStatus(v.getStartTime(), v.getEndTime()));
        }

        return new PageImpl<>(voucherInfoDTOs, pageable, pageVoucherInfoDTOs.getTotalElements());
    }

    @Override
    public Optional<VoucherInfoDTO> findById(Integer entityId) {
        Optional<VoucherInfo> voucherInfo = voucherInfoRepo.findById(entityId);
        if (voucherInfo.isPresent()) {
            VoucherInfoDTO dto = modelMapper.map(voucherInfo.get(), VoucherInfoDTO.class);
            dto.setStatus(genVoucherStatus(dto.getStartTime(), dto.getEndTime()));
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public VoucherInfoDTO save(VoucherInfoDTO voucherInfo) {
        try {
            if (voucherInfo == null) {
                throw new BadRequestException();
            }
            VoucherInfo voucherSaved = voucherInfoRepo.save(VoucherInfo.fromVoucherDTO(voucherInfo));
            //
            for (ProductDTO product : voucherInfo.getApplicableProducts()) {
                VoucherApply voucherApply = new VoucherApply();
                voucherApply.setVoucherId(voucherSaved.getId());
                voucherApply.setProductId(product.getId());
                voucherApplyService.save(voucherApply);
            }
            //Gen list voucher detail
            List<String> listKeyVoucher = new ArrayList<>();
            while (listKeyVoucher.size() < voucherInfo.getQuantity()) {
                String randomKey = "";
                while (randomKey.isEmpty()) {
                    String tempKey = generateRandomKeyVoucher(voucherInfo.getLength(), voucherInfo.getVoucherType());
                    if (voucherTicketService.findByCode(tempKey) == null) {
                        randomKey = tempKey;
                    }
                }
                if (!listKeyVoucher.contains(randomKey)) {
                    VoucherTicket voucherTicket = new VoucherTicket();
                    voucherTicket.setCode(randomKey);
                    voucherTicket.setLength(voucherInfo.getLength());
                    voucherTicket.setVoucherInfo(voucherSaved);
                    voucherTicket.setUsed(false);
                    if (voucherTicketService.save(voucherTicket) != null) {
                        listKeyVoucher.add(randomKey);
                    }
                }
            }
            VoucherInfoDTO dto = modelMapper.map(voucherSaved, VoucherInfoDTO.class);
            dto.setStatus(genVoucherStatus(dto.getStartTime(), dto.getEndTime()));
            systemLogService.writeLogCreate(MODULE.PRODUCT, ACTION.PRO_VOU_C, MasterObject.VoucherInfo, "Thêm mới voucher", dto.getTitle());
            return dto;
        } catch (RuntimeException ex) {
            throw new AppException(ex);
        }
    }

    @Override
    public VoucherInfoDTO update(VoucherInfoDTO voucherInfo, Integer voucherId) {
        try {
            Optional<VoucherInfoDTO> voucherOpt = this.findById(voucherId);
            if (voucherOpt.isEmpty()) {
                throw new ResourceNotFoundException("Voucher not found!");
            }
            VoucherInfoDTO voucherInfoBefore = ObjectUtils.clone(voucherOpt.get());
            voucherInfo.setId(voucherId);
            VoucherInfo voucherInfoUpdated = voucherInfoRepo.save(voucherInfo);

            Map<String, Object[]> logChanges = LogUtils.logChanges(voucherInfoBefore, voucherInfoUpdated);
            systemLogService.writeLogUpdate(MODULE.PRODUCT, ACTION.PRO_VOU_U, MasterObject.VoucherInfo, "Cập nhật voucher " + voucherInfoUpdated.getTitle(), logChanges);

            return modelMapper.map(voucherInfoUpdated, VoucherInfoDTO.class);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.UPDATE_ERROR_OCCURRED.getDescription(), "voucher"), ex);
        }
    }

    @Override
    public String delete(Integer voucherId) {
        Optional<VoucherInfoDTO> voucherInfoBefore = this.findById(voucherId);
        if (voucherInfoBefore.isEmpty()) {
            throw new BadRequestException("Voucher not found!");
        }
        if (!voucherApplyService.findByVoucherId(voucherId).isEmpty()) {
            throw new DataInUseException(ErrorCode.ERROR_DATA_LOCKED.getDescription());
        }
        voucherInfoRepo.deleteById(voucherId);
        systemLogService.writeLogDelete(MODULE.PRODUCT, ACTION.PRO_VOU_D, MasterObject.VoucherInfo, "Xóa voucher", voucherInfoBefore.get().getTitle());
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    private String generateRandomKeyVoucher(int lengthOfKey, String voucherType) {
        String characters = "";
        if (VoucherType.NUMBER.name().equals(voucherType)) {
            characters = "0123456789";
        }
        if (VoucherType.TEXT.name().equals(voucherType)) {
            characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        if (VoucherType.BOTH.name().equals(voucherType)) {
            characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        }

        SecureRandom random = new SecureRandom();
        StringBuilder keyVoucher = new StringBuilder();
        for (int i = 0; i < lengthOfKey; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            keyVoucher.append(randomChar);
        }
        return keyVoucher.toString();
    }

    private String genVoucherStatus(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime currentDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        startTime = startTime.withHour(0).withMinute(0).withSecond(0);
        endTime = endTime.withHour(0).withMinute(0).withSecond(0);

        if ((startTime.isBefore(currentDate) || startTime.equals(currentDate)) && (endTime.isAfter(currentDate) || endTime.equals(currentDate))) {
            return VoucherStatus.A.getLabel();
        } else {
            return VoucherStatus.I.getLabel();
        }
    }
}