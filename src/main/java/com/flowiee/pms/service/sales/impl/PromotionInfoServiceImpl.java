package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.*;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.DataInUseException;
import com.flowiee.pms.model.dto.ProductDTO;
import com.flowiee.pms.model.dto.PromotionApplyDTO;
import com.flowiee.pms.model.dto.PromotionInfoDTO;
import com.flowiee.pms.repository.sales.PromotionInfoRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductInfoService;
import com.flowiee.pms.service.sales.PromotionApplyService;
import com.flowiee.pms.service.sales.PromotionService;
import com.flowiee.pms.utils.MessageUtils;
import com.flowiee.pms.utils.constants.PromotionStatus;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionInfoServiceImpl extends BaseService implements PromotionService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PromotionInfoRepository promotionInfoRepository;
    @Autowired
    private PromotionApplyService promotionApplyService;
    @Autowired
    private ProductInfoService productInfoService;

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
            List<ProductDTO> productDTOs = new ArrayList<>();
            for (PromotionApplyDTO promotionApplyDTO : promotionApplyService.findByPromotionId(p.getId())) {
                Optional<ProductDTO> productDTOOptional = productInfoService.findById(promotionApplyDTO.getProductId());
                productDTOOptional.ifPresent(productDTOs::add);
            }
            p.setApplicableProducts(productDTOs);
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
        if (promotionInfoDTO == null) {
            throw new BadRequestException();
        }
        try {
            PromotionInfo promotionInfo = PromotionInfo.fromDTO(promotionInfoDTO);
            promotionInfo.setStartTime(LocalDateTime.parse(promotionInfoDTO.getStartTimeStr() + "T00:00"));
            promotionInfo.setEndTime(LocalDateTime.parse(promotionInfoDTO.getEndTimeStr() + "T00:00"));

            PromotionInfo promotionInfoSaved = promotionInfoRepository.save(promotionInfo);
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
    public PromotionInfoDTO update(PromotionInfoDTO inputDTO, Integer promotionId) {
        try {
            if (inputDTO == null || promotionId == null || this.findById(promotionId).isEmpty()) {
                throw new BadRequestException();
            }
            PromotionInfo promotionInfo = PromotionInfo.fromDTO(inputDTO);
            promotionInfo.setStartTime(LocalDateTime.parse(inputDTO.getStartTimeStr() + "T00:00"));
            promotionInfo.setEndTime(LocalDateTime.parse(inputDTO.getEndTimeStr() + "T00:00"));
            promotionInfo.setId(promotionId);
            PromotionInfo promotionInfoSaved = promotionInfoRepository.save(promotionInfo);
            return modelMapper.map(promotionInfoSaved, PromotionInfoDTO.class);
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
            return PromotionStatus.A.getLabel();
        } else {
            return PromotionStatus.I.getLabel();
        }
    }
}