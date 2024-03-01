package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;

import com.flowiee.app.model.dto.ProductDTO;
import com.flowiee.app.model.dto.VoucherInfoDTO;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Entity
@Table(name = "pro_product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product extends BaseEntity implements Serializable {
    @Serial
	private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id", nullable = false)
    private Category productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Category brand;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Category unit;

    @Lob
    @Column(name = "description", length = 30000, columnDefinition = "CLOB")
    private String description;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @JsonIgnore
    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductVariant> listVariants;

    @JsonIgnore
    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<FileStorage> listImages;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductHistory> listProductHistories;

    @JsonIgnore
    @Transient
    private FileStorage imageActive;

    @JsonIgnore
    @Transient
    private List<VoucherInfoDTO> listVoucherInfoApply;

    public Product(int id) {
        super.id = id;
    }

    public Product(Integer id, String name) {
        super.id = id;
        this.productName = name;
    }

    public static Product fromProductDTO(ProductDTO dto) {
        Product p = new Product();
        p.setId(dto.getProductId());
        p.setProductType(new Category(dto.getProductTypeId(), dto.getProductTypeName()));
        p.setBrand(new Category(dto.getBrandId(), dto.getBrandName()));
        p.setProductName(dto.getProductName());
        p.setUnit(new Category(dto.getUnitId(), dto.getUnitName()));
        p.setDescription(dto.getProductDes());
        p.setStatus(dto.getProductStatus());
        return p;
    }

    public Map<String, String> compareTo(Product productToCompare) {
        Map<String, String> map = new HashMap<>();
        if (!this.productType.getName().equals(productToCompare.getProductType().getName())) {
            map.put("Product type", this.productType.getName() + "#" + productToCompare.getProductType().getName());
        }
        if (!this.brand.getName().equals(productToCompare.getBrand().getName())) {
            map.put("Brand name", this.brand.getName() + "#" + productToCompare.getBrand().getName());
        }
        if (!this.unit.getName().equals(productToCompare.getUnit().getName())) {
            map.put("Unit name", this.unit.getName() + "#" + productToCompare.getUnit().getName());
        }
        if (!this.productName.equals(productToCompare.getProductName())) {
            map.put("Product name", this.productName + "#" + productToCompare.getProductName());
        }
        if (this.getDescription() == null) this.setDescription("-");
        if (!this.description.equals(productToCompare.getDescription())) {
            String descriptionOld = this.description.length() > 9999 ? this.description.substring(0, 9999) : this.description;
            String descriptionNew = productToCompare.getDescription().length() > 9999 ? productToCompare.getDescription().substring(0, 9999) : productToCompare.getDescription();
            map.put("Product description", descriptionOld + "#" + descriptionNew);
        }
        if (!this.status.equals(productToCompare.getStatus())) {
            map.put("Product status", this.status + "#" + productToCompare.getStatus());
        }
        if ((this.imageActive != null && productToCompare.getImageActive() != null) && !this.imageActive.getId().equals(productToCompare.getImageActive().getId())) {
            map.put("Image active", this.imageActive.getId() + "#" + productToCompare.getId());
        }
        if ((this.listVariants != null && productToCompare.getListVariants() != null) && this.listVariants.size() < productToCompare.getListVariants().size()) {
            map.put("Insert new product variant", "#");
        }
        return map;
    }

	@Override
	public String toString() {
		return "Product [id=" + super.id + ", productType=" + productType + ", brand=" + brand + ", productName=" + productName + ", unit="
				+ unit + ", description=" + description + ", status=" + status + "]";
	}
}