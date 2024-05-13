package com.flowiee.pms.service.sales;

import com.flowiee.pms.service.CrudService;
import com.flowiee.pms.model.dto.PromotionApplyDTO;

import java.util.List;

public interface PromotionApplyService extends CrudService<PromotionApplyDTO> {
    List<PromotionApplyDTO> findAll(Integer promotionId , Integer productId);

    List<PromotionApplyDTO> findByProductId(Integer productId);

    List<PromotionApplyDTO> findByPromotionId(Integer promotionId);
}