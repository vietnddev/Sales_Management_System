package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.VoucherInfo;
import com.flowiee.pms.entity.sales.VoucherTicket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class VoucherInfoDTO extends VoucherInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer length;
    private BigDecimal discountPriceMax;
    private String status;
    private List<VoucherTicket> listVoucherTicket;
    private List<ProductDTO> applicableProducts;

	@Override
	public String toString() {
		return "VoucherInfoDTO [id=" + id + ", maxPriceDiscount=" + discountPriceMax + ", startTime=" + getStartTime()
                + ", endTime=" + getEndTime() + ", status=" + status + ", createdAt=" + getCreatedAt() + ", createdBy=" + createdBy + "]";
	}   
}