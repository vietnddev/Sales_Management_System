package com.flowiee.pms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowiee.pms.entity.sales.LedgerTransaction;
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
    private List<LedgerTransaction> listTransactions;

    @JsonIgnore
    private int totalPages;
    @JsonIgnore
    private long totalElements;
}