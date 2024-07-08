package com.flowiee.pms.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseHistory {
    int customerId;
    int year;
    int month;
    int purchaseQty;
    BigDecimal orderAvgValue;
}