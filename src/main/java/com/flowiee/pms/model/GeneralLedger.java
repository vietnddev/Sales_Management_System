package com.flowiee.pms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowiee.pms.entity.sales.LedgerTransaction;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeneralLedger {
    BigDecimal beginBalance;
    BigDecimal totalReceipt;
    BigDecimal totalPayment;
    BigDecimal endBalance;
    List<LedgerTransaction> listTransactions;

    @JsonIgnore
    int totalPages;
    @JsonIgnore
    long totalElements;
}