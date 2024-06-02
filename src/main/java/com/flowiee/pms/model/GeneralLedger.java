package com.flowiee.pms.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class GeneralLedger {
    private BigDecimal beginBalance;
    private BigDecimal totalReceipt;
    private BigDecimal totalPayment;
    private BigDecimal endBalance;
    private List<Object> listTransactions;
}