package com.flowiee.app.dto;

import com.flowiee.app.entity.FileStorage;
import com.flowiee.app.entity.Product;
import com.flowiee.app.utils.AppConstants;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer productId;
    private Integer productTypeId;
    private String productTypeName;
    private Integer brandId;
    private String brandName;
    private String productName;
    private Integer unitId;
    private String unitName;
    private String productDes;
    private String productStatus;
    private Integer imageActiveId;
    private FileStorage imageActive;
//    private Integer voucherApplyId;
//    private String voucherApplyTitle;
//    private Integer discountPercent;
//    private Float discountMaxPrice;
    private Integer totalQtySell;
    private Integer totalQtyStorage;
    private Integer productVariantQty;
    private Integer soldQty;
    private Date createdAt;
    private Integer createdById;
    private String createdByName;
    private List<VoucherInfoDTO> listVoucherInfoApply;
    private LinkedHashMap<String, String> productVariantInfo;

    public static ProductDTO fromProduct(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getId());
        dto.setProductTypeId(product.getProductType().getId());
        dto.setProductTypeName(product.getProductType().getName());
        dto.setBrandId(product.getBrand().getId());
        dto.setBrandName(product.getBrand().getName());
        dto.setProductName(product.getTenSanPham());
        dto.setUnitId(product.getUnit().getId());
        dto.setUnitName(product.getUnit().getName());
        dto.setProductDes(product.getMoTaSanPham());
        dto.setProductStatus(product.getStatus());
        dto.setImageActiveId(product.getImageActive() != null ? product.getImageActive().getId() : null);
        dto.setImageActive(product.getImageActive());
//      dto.setVoucherApplyId(null);
//      dto.setVoucherApplyTitle(null);
//      dto.setDiscountPercent(null);
//      dto.setDiscountMaxPrice(null);
        dto.setProductVariantQty(product.getListBienThe().size());
        dto.setSoldQty(null);
        dto.setCreatedAt(product.getCreatedAt());
        dto.setCreatedById(product.getCreatedBy());
        dto.setCreatedByName(null);
        dto.setListVoucherInfoApply(product.getListVoucherInfoApply());
        if (AppConstants.PRODUCT_STATUS.ACTIVE.name().equals(product.getStatus())) {
            dto.setProductStatus(AppConstants.PRODUCT_STATUS.ACTIVE.getLabel());
        } else if (AppConstants.PRODUCT_STATUS.INACTIVE.name().equals(product.getStatus())) {
            dto.setProductStatus(AppConstants.PRODUCT_STATUS.INACTIVE.getLabel());
        }
        return dto;
    }

    public static List<ProductDTO> fromProducts(List<Product> listProduct) {
        List<ProductDTO> dataResponse = new ArrayList<>();
        for (Product p : listProduct) {
            dataResponse.add(ProductDTO.fromProduct(p));
        }
        return dataResponse;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductDTO [productId=");
		builder.append(productId);
		builder.append(", productTypeId=");
		builder.append(productTypeId);
		builder.append(", productTypeName=");
		builder.append(productTypeName);
		builder.append(", brandId=");
		builder.append(brandId);
		builder.append(", brandName=");
		builder.append(brandName);
		builder.append(", productName=");
		builder.append(productName);
		builder.append(", unitId=");
		builder.append(unitId);
		builder.append(", unitName=");
		builder.append(unitName);
		builder.append(", productDes=");
		builder.append(productDes);
		builder.append(", productStatus=");
		builder.append(productStatus);
		builder.append(", imageActiveId=");
		builder.append(imageActiveId);
		builder.append(", imageActive=");
		builder.append(imageActive);
		builder.append(", productVariantQty=");
		builder.append(productVariantQty);
		builder.append(", soldQty=");
		builder.append(soldQty);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", createdByName=");
		builder.append(createdByName);
		builder.append(", listVoucherInfoApply=");
		builder.append(listVoucherInfoApply);
		builder.append("]");
		return builder.toString();
	}        
}