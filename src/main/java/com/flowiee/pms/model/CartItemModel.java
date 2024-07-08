package com.flowiee.pms.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CartItemModel {
    private Integer cartId;
    private Integer itemId;
    private String itemName;
    private Integer productComboId;
    private Integer productVariantId;
}