package com.flowiee.pms.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.flowiee.pms.entity.BaseEntity;

import com.flowiee.pms.entity.sales.Items;
import com.flowiee.pms.entity.sales.OrderDetail;
import com.flowiee.pms.entity.sales.Supplier;
import com.flowiee.pms.entity.sales.GarmentFactory;
import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.system.FileStorage;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Entity
@Table(name = "product_detail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductDetail extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(name = "variant_code", length = 50, nullable = false)
    private String variantCode;
    
    @Column(name = "variant_name")
    private String variantName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", nullable = false)
    private Category color;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", nullable = false)
    private Category size;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabric_id", nullable = false)
    private Category fabricType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garment_factory_id")
    private GarmentFactory garmentFactory;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "quantity_stg", nullable = false)
    private Integer storageQty;

    @Column(name = "quantity_sold", nullable = false)
    private Integer soldQty;

    @Column(name = "quantity_defective", nullable = false)
    private Integer defectiveQty;

    @Column(name = "original_price", nullable = false)
    private BigDecimal originalPrice;//Remove in future

    @Column(name = "discount_price", nullable = false)
    private BigDecimal discountPrice;//Remove in future

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "cost_price")
    private BigDecimal costPrice;

    @Column(name = "retail_price", nullable = false)
    private BigDecimal retailPrice;

    @Column(name = "retail_price_discount")
    private BigDecimal retailPriceDiscount;

    @Column(name = "wholesale_price", nullable = false)
    private BigDecimal wholesalePrice;

    @Column(name = "wholesale_price_discount")
    private BigDecimal wholesalePriceDiscount;

    @Column(name = "weight")
    private String weight;

    @Column(name = "dimensions")
    private String dimensions;

    @Column(name = "sku")
    private String sku;

    @Column(name = "warranty_period")
    private Integer warrantyPeriod;

    @Column(name = "sole_material")
    private String soleMaterial;

    @Column(name = "heel_height")
    private String heelHeight;

    @Column(name = "discontinued_date")
    private LocalDate discontinuedDate;

    @Column(name = "is_limited_edition")
    private Boolean isLimitedEdition;

    @Column(name = "pattern")
    private String pattern;

    @Column(name = "note")
    private String note;

    @Column(name = "status", nullable = false)
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "productDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductAttribute> listAttributes;

    @JsonIgnore
    @OneToMany(mappedBy = "productDetail", fetch = FetchType.LAZY)
    private List<OrderDetail> listOrderDetail;

    @JsonIgnore
    @OneToMany(mappedBy = "productDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<FileStorage> listImages;

    @JsonIgnore
    @OneToMany(mappedBy = "productDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Items> listItems;

    @JsonIgnore
    @OneToMany(mappedBy = "productDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductHistory> listProductHistories;

    @JsonIgnore
    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductVariantTemp> listProductVariantTemp;

    public ProductDetail(int id) {
        super.id = id;
    }

    public Map<String, String> compareTo(ProductDetail entityToCompare) {
        Map<String, String> map = new HashMap<>();
        if (!this.getVariantCode().equals(entityToCompare.getVariantCode())) {
            map.put("Product code", this.getVariantCode() + "#" + entityToCompare.getVariantCode());
        }
        if (!this.getVariantName().equals(entityToCompare.getVariantName())) {
            map.put("Product name", this.getVariantName() + "#" + entityToCompare.getVariantName());
        }
        if (!this.getColor().getName().equals(entityToCompare.getColor().getName())) {
            map.put("Product color", this.getColor().getName() + "#" + entityToCompare.getColor().getName());
        }
        if (!this.getSize().getName().equals(entityToCompare.getSize().getName())) {
            map.put("Product size", this.getSize().getName() + "#" + entityToCompare.getSize().getName());
        }
        if (!this.getFabricType().getName().equals(entityToCompare.getFabricType().getName())) {
            map.put("Product fabric", this.getFabricType().getName() + "#" + entityToCompare.getFabricType().getName());
        }
        return map;
    }

	@Override
	public String toString() {
		return "ProductVariant [id=" + super.id + ", variantCode=" + variantCode + ", variantName=" + variantName + ", status=" + status;
	}
}