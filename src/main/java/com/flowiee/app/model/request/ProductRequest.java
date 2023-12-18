package com.flowiee.app.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
	private Integer productId;
    private Integer productType;
    private Integer brand;
    private String productName;
    private Integer unit;
    private String description;
    private String status;
}