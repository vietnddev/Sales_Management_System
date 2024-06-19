package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.PromotionApply;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionApplyDTO extends PromotionApply implements Serializable {
    @Serial
    static final long serialVersionUID = 1L;

    Integer promotionApplyId;
    Integer promotionInfoId;
    String promotionInfoTitle;
    Integer productId;
    String productName;
    String appliedAt;
    Integer appliedBy;
}