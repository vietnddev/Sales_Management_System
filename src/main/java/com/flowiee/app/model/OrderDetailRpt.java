package com.flowiee.app.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailRpt {
    private String productName;
    private Float unitPrice;
    private int quantity;
    private Float subTotal;
    private String note;
}