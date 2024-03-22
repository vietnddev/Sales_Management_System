package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.product.ProductHistory;
import com.flowiee.pms.utils.AppConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductVariantDTO extends ProductDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer productId;
    private Integer storageQty;
    private Integer soldQty;
    private Integer productTypeId;
    private String productTypeName;
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
    private Integer ticketImportGoodsId;
    private String ticketImportGoodsTitle;
    private BigDecimal originalPrice;
    private BigDecimal discountPrice;
    private Float priceMaxDiscount;
    private Float priceAfterDiscount;
    private String unitCurrency;
    private List<ProductHistory> listPrices;

    public static ProductVariantDTO fromProductVariant(ProductDetail input) {
        if (input == null) {
            return null;
        }
        ProductVariantDTO dto = new ProductVariantDTO();
        dto.setId(input.getId());
        dto.setProductId(input.getProduct().getId());
        dto.setVariantCode(input.getVariantCode());
        dto.setVariantName(input.getVariantName());
        dto.setStorageQty(input.getStorageQty());
        dto.setSoldQty(input.getSoldQty());
        dto.setStatus(input.getStatus());
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
        dto.setOriginalPrice(input.getOriginalPrice());
        dto.setDiscountPrice(input.getDiscountPrice());
        dto.setPriceMaxDiscount(null);
        dto.setPriceAfterDiscount(null);
        dto.setUnitCurrency(null);
        if (AppConstants.PRODUCT_STATUS.A.name().equals(input.getStatus())) {
            dto.setStatus(AppConstants.PRODUCT_STATUS.A.getLabel());
        } else if (AppConstants.PRODUCT_STATUS.I.name().equals(input.getStatus())) {
            dto.setStatus(AppConstants.PRODUCT_STATUS.I.getLabel());
        }
        return dto;
    }

    public static List<ProductVariantDTO> fromProductVariants(List<ProductDetail> productDetails) {
        List<ProductVariantDTO> list = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(productDetails)) {
            for (ProductDetail p : productDetails) {
                list.add(ProductVariantDTO.fromProductVariant(p));
            }
        }
        return list;
    }

	@Override
	public String toString() {
		return "ProductVariantDTO [id=" + id + ", code=" + getVariantCode() + ", name=" + getVariantName()
                + ", storageQty=" + storageQty + ", soldQty=" + soldQty + ", status=" + getStatus() + ", colorId=" + colorId + ", sizeId=" + sizeId
                + ", fabricTypeId=" + fabricTypeId + ", garmentFactoryId=" + garmentFactoryId + ", supplierId=" + supplierId
				+ ", ticketImportGoodsId=" + ticketImportGoodsId + "]";
	}        
}