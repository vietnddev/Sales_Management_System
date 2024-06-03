package com.flowiee.pms.service.sales;

import com.flowiee.pms.service.BaseCurd;
import com.flowiee.pms.model.dto.PromotionApplyDTO;

import java.util.List;

public interface PromotionApplyService extends BaseCurd<PromotionApplyDTO> {
    List<PromotionApplyDTO> findAll(Integer promotionId , Integer productId);

    List<PromotionApplyDTO> findByPromotionId(Integer promotionId);
}