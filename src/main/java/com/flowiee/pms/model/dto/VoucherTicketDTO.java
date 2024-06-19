package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.VoucherTicket;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherTicketDTO extends VoucherTicket implements Serializable {
	@Serial
	static final long serialVersionUID = 1L;
	
	String available;
}