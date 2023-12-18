package com.flowiee.app.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantRequest {
	private Integer productId;
	private String productName;
	private Integer productVariantId;            
    private String description;
    private Integer quantity;
    private String status;
    private Integer colorId;    
    private Integer sizeId;
    private Integer fabricTypeId;
    private Integer garmentFactoryId;
    private Integer supplierId;    
    private Integer ticketImportId;
}
