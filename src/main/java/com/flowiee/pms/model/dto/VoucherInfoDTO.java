package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.VoucherInfo;
import com.flowiee.pms.entity.sales.VoucherTicket;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherInfoDTO extends VoucherInfo implements Serializable {
    @Serial
    static final long serialVersionUID = 1L;

    String startTimeStr;
    String endTimeStr;
    Integer length;
    BigDecimal discountPriceMax;
    String status;
    List<VoucherTicket> listVoucherTicket;
    List<ProductDTO> applicableProducts;

	@Override
	public String toString() {
		return "VoucherInfoDTO [id=" + id + ", maxPriceDiscount=" + discountPriceMax + ", startTime=" + getStartTime()
                + ", endTime=" + getEndTime() + ", status=" + status + ", createdAt=" + getCreatedAt() + ", createdBy=" + createdBy + "]";
	}   
}