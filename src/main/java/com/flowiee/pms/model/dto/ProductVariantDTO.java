package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.product.ProductHistory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariantDTO extends ProductDetail implements Serializable {
    @Serial
    static final long serialVersionUID = 1L;

    Integer productId;
    Integer productTypeId;
    String productTypeName;
    Integer brandId;
    String brandName;
    Integer unitId;
    String  unitName;
    Integer colorId;
    String colorName;
    Integer sizeId;
    String sizeName;
    Integer fabricTypeId;
    String fabricTypeName;
    Integer garmentFactoryId;
    String garmentFactoryName;
    Integer supplierId;
    String supplierName;
    //Integer ticketImportGoodsId;
    //String ticketImportGoodsTitle;
    BigDecimal originalPrice;
    BigDecimal discountPrice;
    Float priceMaxDiscount;
    Float priceAfterDiscount;
    String unitCurrency;
    List<ProductHistory> listPrices;
    Integer storageIdInitStorageQty;
    Integer storageIdInitSoldQty;

	@Override
	public String toString() {
		return "ProductVariantDTO [id=" + id + ", code=" + getVariantCode() + ", name=" + getVariantName()
                + ", storageQty=" + getStorageQty() + ", soldQty=" + getSoldQty() + ", status=" + getStatus() + ", colorId=" + colorId + ", sizeId=" + sizeId
                + ", fabricTypeId=" + fabricTypeId + ", garmentFactoryId=" + garmentFactoryId + ", supplierId=" + supplierId + "]";
	}        
}