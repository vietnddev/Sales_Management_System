package com.flowiee.pms.service.sales;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.model.dto.PromotionApplyDTO;

import java.util.List;

public interface PromotionApplyService extends BaseService<PromotionApplyDTO> {
    List<PromotionApplyDTO> findAll(Integer promotionId , Integer productId);

    List<PromotionApplyDTO> findByProductId(Integer productId);

    List<PromotionApplyDTO> findByPromotionId(Integer promotionId);
}