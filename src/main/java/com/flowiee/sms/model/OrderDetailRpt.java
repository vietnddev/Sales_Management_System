package com.flowiee.sms.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDetailRpt {
    private String productName;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal subTotal;
    private String note;
}