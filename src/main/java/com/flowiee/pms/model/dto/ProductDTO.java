package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.product.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO extends Product implements Serializable {
    @Serial
    static final long serialVersionUID = 1L;

    Integer productTypeId;
    String productTypeName;
    Integer brandId;
    String brandName;
    Integer unitId;
    String unitName;
    String imageActive;
    Integer totalQtySell;
    Integer totalQtyStorage;
    Integer totalDefective;
    Integer totalQtyAvailableSales;
    Integer productVariantQty;
    Integer soldQty;
    List<VoucherInfoDTO> listVoucherInfoApply;
    LinkedHashMap<String, String> productVariantInfo;

    public Map<String, String> compareTo(ProductDTO productToCompare) {
        Map<String, String> map = new HashMap<>();
        if (!getProductType().getName().equals(productToCompare.getProductTypeName())) {
            map.put("Product type", getProductType().getName() + "#" + productToCompare.getProductType().getName());
        }
        if (!getBrand().getName().equals(productToCompare.getBrand().getName())) {
            map.put("Brand name", getBrand().getName() + "#" + productToCompare.getBrand().getName());
        }
        if (!getUnit().getName().equals(productToCompare.getUnit().getName())) {
            map.put("Unit name", getUnit().getName() + "#" + productToCompare.getUnit().getName());
        }
        if (!getProductName().equals(productToCompare.getProductName())) {
            map.put("Product name", getProductName() + "#" + productToCompare.getProductName());
        }
        if (getDescription() == null) this.setDescription("-");
        if (!getDescription().equals(productToCompare.getDescription())) {
            String descriptionOld = getDescription().length() > 9999 ? getDescription().substring(0, 9999) : getDescription();
            String descriptionNew = productToCompare.getDescription().length() > 9999 ? productToCompare.getDescription().substring(0, 9999) : productToCompare.getDescription();
            map.put("Product description", descriptionOld + "#" + descriptionNew);
        }
        if (!getStatus().equals(productToCompare.getStatus())) {
            map.put("Product status", getStatus() + "#" + productToCompare.getStatus());
        }
//        if ((this.imageActive != null && productToCompare.getImageActive() != null) && !this.imageActive.getId().equals(productToCompare.getImageActive().getId())) {
//            map.put("Image active", this.imageActive.getId() + "#" + productToCompare.getId());
//        }
        if ((getListVariants() != null && productToCompare.getListVariants() != null) && getListVariants().size() < productToCompare.getListVariants().size()) {
            map.put("Insert new product variant", "#");
        }
        return map;
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