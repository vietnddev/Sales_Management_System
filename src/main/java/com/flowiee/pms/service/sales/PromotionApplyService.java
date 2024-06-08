package com.flowiee.pms.service.sales;

import com.flowiee.pms.service.BaseCurdService;
import com.flowiee.pms.model.dto.PromotionApplyDTO;

import java.util.List;

public interface PromotionApplyService extends BaseCurdService<PromotionApplyDTO> {
    List<PromotionApplyDTO> findAll(Integer promotionId , Integer productId);

    List<PromotionApplyDTO> findByPromotionId(Integer promotionId);
}