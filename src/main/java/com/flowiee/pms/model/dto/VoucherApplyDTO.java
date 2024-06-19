package com.flowiee.pms.model.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherApplyDTO implements Serializable {
    @Serial
    static final long serialVersionUID = 1L;

    Integer voucherApplyId;
    Integer voucherInfoId;
    String voucherInfoTitle;
    Integer productId;
    String productName;
    String appliedAt;
    Integer appliedBy;
    
	@Override
	public String toString() {
		return "VoucherApplyDTO [voucherApplyId=" + voucherApplyId + ", voucherInfoId=" + voucherInfoId
				+ ", voucherInfoTitle=" + voucherInfoTitle + ", productId=" + productId + ", productName=" + productName
				+ ", appliedAt=" + appliedAt + ", appliedBy=" + appliedBy + "]";
	}    
}