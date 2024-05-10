package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.*;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.DataInUseException;
import com.flowiee.pms.model.dto.ProductDTO;
import com.flowiee.pms.model.dto.PromotionApplyDTO;
import com.flowiee.pms.model.dto.PromotionInfoDTO;
import com.flowiee.pms.repository.sales.PromotionInfoRepository;
import com.flowiee.pms.service.sales.PromotionApplyService;
import com.flowiee.pms.service.sales.PromotionService;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.MessageUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionInfoServiceImpl implements PromotionService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PromotionInfoRepository promotionInfoRepository;
    @Autowired
    private PromotionApplyService promotionApplyService;

    @Override
    public List<PromotionInfoDTO> findAll() {
        Page<PromotionInfoDTO> promotionInfos = this.findAll(-1, -1, null, null, null, null);
        return promotionInfos.getContent();
    }

    @Override
    public Page<PromotionInfoDTO> findAll(int pageSize, int pageNum, String pTitle, LocalDateTime pStartTime, LocalDateTime pEndTime, String pStatus) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        }
        if (pEndTime == null) {
            pEndTime = LocalDateTime.of(2100, 12, 31, 0, 0);
        }
        if (pStartTime == null) {
            pStartTime = LocalDateTime.of(1900, 1, 1, 0, 0);;
        }
        Page<PromotionInfo> pagePromotionInfos = promotionInfoRepository.findAll(pTitle, pStartTime, pEndTime, pStatus, pageable);

        Type listType = new TypeToken<List<PromotionInfoDTO>>() {}.getType();
        List<PromotionInfoDTO> promotionInfoDTOs = modelMapper.map(pagePromotionInfos.getContent(), listType);

        for (PromotionInfoDTO p : promotionInfoDTOs) {
            p.setStatus(genPromotionStatus(p.getStartTime(), p.getEndTime()));
        }

        return new PageImpl<>(promotionInfoDTOs, pageable, pagePromotionInfos.getTotalElements());
    }

    @Override
    public Optional<PromotionInfoDTO> findById(Integer entityId) {
        Optional<PromotionInfo> promotionInfo = promotionInfoRepository.findById(entityId);
        if (promotionInfo.isPresent()) {
            PromotionInfoDTO dto = modelMapper.map(promotionInfo.get(), PromotionInfoDTO.class);
            dto.setStatus(genPromotionStatus(dto.getStartTime(), dto.getEndTime()));
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public PromotionInfoDTO save(PromotionInfoDTO promotionInfoDTO) {
        try {
            if (promotionInfoDTO == null) {
                throw new BadRequestException();
            }
            PromotionInfo promotionInfoSaved = promotionInfoRepository.save(promotionInfoDTO);
            for (ProductDTO product : promotionInfoDTO.getApplicableProducts()) {
                PromotionApplyDTO promotionApply = new PromotionApplyDTO();
                promotionApply.setPromotionId(promotionInfoSaved.getId());
                promotionApply.setProductId(product.getId());
                promotionApplyService.save(promotionApply);
            }
            PromotionInfoDTO dto = modelMapper.map(promotionInfoSaved, PromotionInfoDTO.class);
            dto.setStatus(genPromotionStatus(dto.getStartTime(), dto.getEndTime()));
            return dto;
        } catch (RuntimeException ex) {
            throw new AppException(ex);
        }
    }

    @Override
    public PromotionInfoDTO update(PromotionInfoDTO promotionInfo, Integer promotionId) {
        try {
            if (promotionInfo == null || promotionId == null || this.findById(promotionId).isEmpty()) {
                throw new BadRequestException();
            }
            promotionInfo.setId(promotionId);
            return promotionInfoRepository.save(promotionInfo);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "promotion"), ex);
        }
    }

    @Override
    public String delete(Integer promotionId) {
        if (this.findById(promotionId).isEmpty()) {
            throw new BadRequestException("Promotion not found!");
        }
        if (!promotionApplyService.findByPromotionId(promotionId).isEmpty()) {
            throw new DataInUseException(MessageUtils.ERROR_DATA_LOCKED);
        }
        promotionInfoRepository.deleteById(promotionId);
        return MessageUtils.DELETE_SUCCESS;
    }

    private String genPromotionStatus(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime currentDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        startTime = startTime.withHour(0).withMinute(0).withSecond(0);
        endTime = endTime.withHour(0).withMinute(0).withSecond(0);

        if ((startTime.isBefore(currentDate) || startTime.equals(currentDate)) && (endTime.isAfter(currentDate) || endTime.equals(currentDate))) {
            return AppConstants.PROMOTION_STATUS.A.getLabel();
        } else {
            return AppConstants.PROMOTION_STATUS.I.getLabel();
        }
    }
}