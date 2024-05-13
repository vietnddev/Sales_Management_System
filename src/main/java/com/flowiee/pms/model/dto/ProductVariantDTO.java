package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.product.ProductHistory;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductVariantDTO extends ProductDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer productId;
    private Integer productTypeId;
    private String productTypeName;
    private Integer brandId;
    private String brandName;
    private Integer unitId;
    private String  unitName;
    private Integer colorId;
    private String colorName;
    private Integer sizeId;
    private String sizeName;
    private Integer fabricTypeId;
    private String fabricTypeName;
    private Integer garmentFactoryId;
    private String garmentFactoryName;
    private Integer supplierId;
    private String supplierName;
    //private Integer ticketImportGoodsId;
    //private String ticketImportGoodsTitle;
    private BigDecimal originalPrice;
    private BigDecimal discountPrice;
    private Float priceMaxDiscount;
    private Float priceAfterDiscount;
    private String unitCurrency;
    private Integer availableSalesQty;
    private List<ProductHistory> listPrices;
    private Integer storageIdInitStorageQty;
    private Integer storageIdInitSoldQty;

	@Override
	public String toString() {
		return "ProductVariantDTO [id=" + id + ", code=" + getVariantCode() + ", name=" + getVariantName()
                + ", storageQty=" + getStorageQty() + ", soldQty=" + getSoldQty() + ", status=" + getStatus() + ", colorId=" + colorId + ", sizeId=" + sizeId
                + ", fabricTypeId=" + fabricTypeId + ", garmentFactoryId=" + garmentFactoryId + ", supplierId=" + supplierId + "]";
	}        
}