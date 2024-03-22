package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.VoucherTicket;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class VoucherTicketDTO extends VoucherTicket implements Serializable {
    private String available;
}