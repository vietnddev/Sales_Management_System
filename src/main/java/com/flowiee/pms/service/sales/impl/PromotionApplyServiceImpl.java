package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.PromotionApply;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.dto.PromotionApplyDTO;
import com.flowiee.pms.repository.sales.PromotionApplyRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.PromotionApplyService;
import com.flowiee.pms.utils.MessageUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionApplyServiceImpl extends BaseService implements PromotionApplyService {
    @Autowired
    private PromotionApplyRepository promotionApplyRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PromotionApplyDTO> findAll(Integer voucherInfoId , Integer productId) {
        return List.of();
    }

    @Override
    public List<PromotionApplyDTO> findAll() {
        return List.of();
    }

    @Override
    public List<PromotionApplyDTO> findByPromotionId(Integer voucherId) {
        List<PromotionApply> promotionApply = promotionApplyRepository.findByPromotionId(voucherId);
        Type listType = new TypeToken<List<PromotionApplyDTO>>() {}.getType();
        return modelMapper.map(promotionApply, listType);
    }

    @Override
    public Optional<PromotionApplyDTO> findById(Integer promotionId) {
        Optional<PromotionApply> promotionApply = promotionApplyRepository.findById(promotionId);
        if (promotionApply.isPresent()) {
            PromotionApplyDTO dto = modelMapper.map(promotionApply.get(), PromotionApplyDTO.class);
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public PromotionApplyDTO save(PromotionApplyDTO promotionApplyDTO) {
        PromotionApply promotionApply = PromotionApply.fromDTO(promotionApplyDTO);
        PromotionApply promotionApplySaved = promotionApplyRepository.save(promotionApply);
        return modelMapper.map(promotionApplySaved, PromotionApplyDTO.class);
    }

    @Override
    public PromotionApplyDTO update(PromotionApplyDTO PromotionApplyDTO, Integer id) {
        if (this.findById(id).isEmpty()) {
            throw new BadRequestException();
        }
        PromotionApplyDTO.setId(id);
        return promotionApplyRepository.save(PromotionApplyDTO);
    }

    @Override
    public String delete(Integer entityId) {
        if (this.findById(entityId).isEmpty()) {
            promotionApplyRepository.deleteById(entityId);
        }
        return MessageUtils.DELETE_SUCCESS;
    }

//    private List<PromotionApplyDTO> extractDataQuery(List<Object[]> objects) {
//        List<PromotionApplyDTO> dataResponse = new ArrayList<>();
//        for (Object[] data : objects) {
//            PromotionApplyDTO promotionApplyDTO = new PromotionApplyDTO();
//            promotionApplyDTO.setPromotionApplyId(Integer.parseInt(String.valueOf(data[0])));
//            promotionApplyDTO.setPromotionId(Integer.parseInt(String.valueOf(data[1])));
//            promotionApplyDTO.setPromotionInfoTitle(String.valueOf(data[2]));
//            promotionApplyDTO.setProductId(Integer.parseInt(String.valueOf(data[3])));
//            promotionApplyDTO.setProductName(String.valueOf(data[4]));
//            promotionApplyDTO.setAppliedAt((String.valueOf(data[5])).substring(0, 10));
//            promotionApplyDTO.setAppliedBy(Integer.parseInt(String.valueOf(data[6])));
//            dataResponse.add(promotionApplyDTO);
//        }
//        return dataResponse;
//    }
}