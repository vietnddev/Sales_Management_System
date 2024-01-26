package com.flowiee.app.dto;

import com.flowiee.app.entity.*;
import com.flowiee.app.utils.AppConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ProductVariantDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer productVariantId;
    private Integer productId;
    private String code;
    private String name;
    private Integer storageQty;
    private Integer soldQty;
    private String description;
    private String status;
    private Integer productTypeId;
    private String productTypeName;
    private Integer unitId;
    private String unitName;
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
    private Integer ticketImportGoodsId;
    private String ticketImportGoodsTitle;
    private Integer discountPercent;
    private Integer priceBuyId;
    private Float priceBuyValue;
    private Integer priceSellId;
    private Double priceSellValue;
    private Float priceMaxDiscount;
    private Float priceAfterDiscount;
    private String unitCurrency;
    private Integer promotionPriceId;
    private Double promotionPriceValue;
    private List<PriceDTO> listPrices;

    public static ProductVariantDTO fromProductVariant(ProductVariant input) {
        ProductVariantDTO dto = new ProductVariantDTO();
        dto.setProductVariantId(input.getId());
        dto.setProductId(input.getProduct().getId());
        dto.setCode(input.getMaSanPham());
        dto.setName(input.getTenBienThe());
        dto.setStorageQty(input.getSoLuongKho());
        dto.setSoldQty(input.getSoLuongDaBan());
        dto.setDescription(input.getMaSanPham());
        dto.setStatus(input.getTrangThai());
        if (ObjectUtils.isNotEmpty(input.getProduct().getProductType())) {
            dto.setProductTypeId(input.getProduct().getProductType().getId());
            dto.setProductTypeName(input.getProduct().getProductType().getName());
        }
        if (ObjectUtils.isNotEmpty(input.getProduct().getUnit())) {
            dto.setUnitId(input.getProduct().getUnit().getId());
            dto.setUnitName(input.getProduct().getUnit().getName());
        }
        dto.setColorId(input.getColor().getId());
        dto.setColorName(input.getColor().getName());
        dto.setSizeId(input.getSize().getId());
        dto.setSizeName(input.getSize().getName());
        dto.setFabricTypeId(input.getFabricType().getId());
        dto.setFabricTypeName(input.getFabricType().getName());
        if (ObjectUtils.isNotEmpty(input.getGarmentFactory())) {
            dto.setGarmentFactoryId(input.getGarmentFactory().getId());
            dto.setGarmentFactoryName(input.getGarmentFactory().getName());
        }
        if (ObjectUtils.isNotEmpty(input.getSupplier())) {
            dto.setSupplierId(input.getSupplier().getId());
            dto.setSupplierName(input.getSupplier().getName());
        }
        if (ObjectUtils.isNotEmpty(input.getTicketImport())) {
            dto.setTicketImportGoodsId(input.getTicketImport().getId());
            dto.setTicketImportGoodsTitle(input.getTicketImport().getTitle());
        }
        dto.setDiscountPercent(null);
        dto.setPriceBuyId(null);
        dto.setPriceBuyValue(null);
        dto.setPriceSellId(null);
        dto.setPriceSellValue(null);
        dto.setPriceMaxDiscount(null);
        dto.setPriceAfterDiscount(null);
        dto.setUnitCurrency(null);
        dto.setListPrices(null);
        if (AppConstants.PRODUCT_STATUS.ACTIVE.name().equals(input.getTrangThai())) {
            dto.setStatus(AppConstants.PRODUCT_STATUS.ACTIVE.getLabel());
        } else if (AppConstants.PRODUCT_STATUS.INACTIVE.name().equals(input.getTrangThai())) {
            dto.setStatus(AppConstants.PRODUCT_STATUS.INACTIVE.getLabel());
        }
        return dto;
    }

	@Override
	public String toString() {
		return "ProductVariantDTO [productVariantId=" + productVariantId + ", productId=" + productId + ", code=" + code
				+ ", name=" + name + ", storageQty=" + storageQty + ", soldQty=" + soldQty + ", description="
				+ description + ", status=" + status + ", colorId=" + colorId + ", colorName=" + colorName + ", sizeId="
				+ sizeId + ", sizeName=" + sizeName + ", fabricTypeId=" + fabricTypeId + ", fabricTypeName="
				+ fabricTypeName + ", garmentFactoryId=" + garmentFactoryId + ", garmentFactoryName="
				+ garmentFactoryName + ", supplierId=" + supplierId + ", supplierName=" + supplierName
				+ ", ticketImportGoodsId=" + ticketImportGoodsId + ", ticketImportGoodsTitle=" + ticketImportGoodsTitle
				+ ", discountPercent=" + discountPercent + ", priceBuyId=" + priceBuyId + ", priceBuyValue="
				+ priceBuyValue + ", priceSellId=" + priceSellId + ", priceSellValue=" + priceSellValue
				+ ", priceMaxDiscount=" + priceMaxDiscount + ", priceAfterDiscount=" + priceAfterDiscount
				+ ", unitCurrency=" + unitCurrency + "]";
	}        
}