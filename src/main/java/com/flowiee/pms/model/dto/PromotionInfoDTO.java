package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.PromotionInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PromotionInfoDTO extends PromotionInfo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	
	private String startTimeStr;
    private String endTimeStr;
    private String status;
    private List<ProductDTO> applicableProducts;
}