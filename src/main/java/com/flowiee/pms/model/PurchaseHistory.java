package com.flowiee.pms.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseHistory {
    private int customerId;
    private int year;
    private int month;
    private int purchaseQty;
    private BigDecimal orderAvgValue;
}