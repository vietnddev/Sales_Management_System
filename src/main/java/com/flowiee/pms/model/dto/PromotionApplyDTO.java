package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.PromotionApply;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class PromotionApplyDTO extends PromotionApply implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer promotionApplyId;
    private Integer promotionInfoId;
    private String promotionInfoTitle;
    private Integer productId;
    private String productName;
    private String appliedAt;
    private Integer appliedBy;
}