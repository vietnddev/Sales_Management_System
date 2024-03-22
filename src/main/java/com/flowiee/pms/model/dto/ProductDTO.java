package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.product.Product;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
public class ProductDTO extends Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer productTypeId;
    private String productTypeName;
    private Integer brandId;
    private String brandName;
    private Integer unitId;
    private String unitName;
    private String imageActive;
    private Integer totalQtySell;
    private Integer totalQtyStorage;
    private Integer productVariantQty;
    private Integer soldQty;
    private List<VoucherInfoDTO> listVoucherInfoApply;
    private LinkedHashMap<String, String> productVariantInfo;

    public static ProductDTO fromProduct(Product product) {
        ProductDTO dto = new ProductDTO();
        if (product != null) {
            dto.setId(product.getId());
            if (ObjectUtils.isNotEmpty(product.getProductType())) {
                dto.setProductTypeId(product.getProductType().getId());
                dto.setProductTypeName(product.getProductType().getName());
            }
            if (ObjectUtils.isNotEmpty(product.getBrand())) {
                dto.setBrandId(product.getBrand().getId());
                dto.setBrandName(product.getBrand().getName());
            }
            dto.setProductName(product.getProductName());
            if (ObjectUtils.isNotEmpty(product.getUnit())) {
                dto.setUnitId(product.getUnit().getId());
                dto.setUnitName(product.getUnit().getName());
            }
            dto.setDescription(product.getDescription());
            dto.setStatus(product.getStatus());
            if (ObjectUtils.isNotEmpty(product.getListVariants())) {
                dto.setProductVariantQty(product.getListVariants().size());
            }
            dto.setSoldQty(null);
            dto.setCreatedAt(product.getCreatedAt());
            dto.setCreatedBy(product.getCreatedBy());
        }
        return dto;
    }

    public static List<ProductDTO> fromProducts(Page<Product> products) {
        List<ProductDTO> list = new ArrayList<>();
        if (products != null) {
            for (Product p : products.getContent()) {
                list.add(ProductDTO.fromProduct(p));
            }
        }
        return list;
    }

    public static List<ProductDTO> fromProducts(List<Product> listProduct) {
        List<ProductDTO> dataResponse = new ArrayList<>();
        if (listProduct != null) {
            for (Product p : listProduct) {
                dataResponse.add(ProductDTO.fromProduct(p));
            }
        }
        return dataResponse;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductDTO [");
		builder.append(", productTypeId=").append(productTypeId);
		builder.append(", brandId=").append(brandId);
		builder.append(", unitId=").append(unitId);
		builder.append(", status=").append(getStatus());
		builder.append(", productVariantQty=").append(productVariantQty);
		builder.append(", soldQty=").append(soldQty);
		builder.append(", createdAt=").append(getCreatedAt());
		builder.append(", createdByName=").append(getCreatedBy());
		builder.append("]");
		return builder.toString();
	}        
}