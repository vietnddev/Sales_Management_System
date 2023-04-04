package com.flowiee.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private int productID;
    private String productName;
    private int quantity;
}
