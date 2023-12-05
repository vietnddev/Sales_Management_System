package com.flowiee.app.dto;

import com.flowiee.app.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

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
    private String productDescription;
    private String productStatus;
    private Integer imageActiveId;
    private Integer voucherApplyId;
    private String voucherApplyTitle;
    private Integer discountPercent;
    private Float discountMaxPrice;
    private Integer productVariantQty;
    private Integer soldQty;
    private Date createdAt;
    private Integer createdById;
    private String createdByName;

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
        dto.setProductDescription(product.getMoTaSanPham());
        dto.setProductStatus(product.getStatus());
        dto.setImageActiveId(product.getImageActive().getId());
        dto.setVoucherApplyId();
        dto.setVoucherApplyTitle();
        dto.setDiscountPercent();
        dto.setDiscountMaxPrice();
        dto.setProductVariantQty();
        dto.setSoldQty();
        dto.setCreatedAt();
        dto.setCreatedById();
        dto.setCreatedByName();
        return dto;
    }
}