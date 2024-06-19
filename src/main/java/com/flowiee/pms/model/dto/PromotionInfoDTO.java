package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.PromotionInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionInfoDTO extends PromotionInfo implements Serializable {
	@Serial
	static final long serialVersionUID = 1L;
	
	String startTimeStr;
    String endTimeStr;
    String status;
    List<ProductDTO> applicableProducts;
}