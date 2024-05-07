package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.PromotionInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PromotionInfoDTO extends PromotionInfo implements Serializable {
    private String status;
    private List<ProductDTO> applicableProducts;
}