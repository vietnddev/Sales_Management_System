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
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetail extends BaseEntity implements Serializable {
    @Serial
    static final long serialVersionUID = 1L;

    @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;
    
    @Column(name = "variant_code", length = 50, nullable = false)
    String variantCode;
    
    @Column(name = "variant_name")
    String variantName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", nullable = false)
    Category color;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", nullable = false)
    Category size;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabric_id", nullable = false)
    Category fabricType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garment_factory_id")
    GarmentFactory garmentFactory;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    Supplier supplier;

    @Column(name = "quantity_stg", nullable = false)
    Integer storageQty;

    @Column(name = "quantity_sold", nullable = false)
    Integer soldQty;

    @Column(name = "quantity_defective", nullable = false)
    Integer defectiveQty;

    @Column(name = "original_price", nullable = false)
    BigDecimal originalPrice;//Remove in future

    @Column(name = "discount_price", nullable = false)
    BigDecimal discountPrice;//Remove in future

    @Column(name = "purchase_price")
    BigDecimal purchasePrice;

    @Column(name = "cost_price")
    BigDecimal costPrice;

    @Column(name = "retail_price", nullable = false)
    BigDecimal retailPrice;

    @Column(name = "retail_price_discount")
    BigDecimal retailPriceDiscount;

    @Column(name = "wholesale_price", nullable = false)
    BigDecimal wholesalePrice;

    @Column(name = "wholesale_price_discount")
    BigDecimal wholesalePriceDiscount;

    @Column(name = "weight")
    String weight;

    @Column(name = "dimensions")
    String dimensions;

    @Column(name = "sku")
    String sku;

    @Column(name = "warranty_period")
    Integer warrantyPeriod;

    @Column(name = "sole_material")
    String soleMaterial;

    @Column(name = "heel_height")
    String heelHeight;

    @Column(name = "discontinued_date")
    LocalDate discontinuedDate;

    @Column(name = "is_limited_edition")
    Boolean isLimitedEdition;

    @Column(name = "pattern")
    String pattern;

    @Column(name = "note")
    String note;

    @Column(name = "status", nullable = false)
    String status;

    @JsonIgnore
    @OneToMany(mappedBy = "productDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<ProductAttribute> listAttributes;

    @JsonIgnore
    @OneToMany(mappedBy = "productDetail", fetch = FetchType.LAZY)
    List<OrderDetail> listOrderDetail;

    @JsonIgnore
    @OneToMany(mappedBy = "productDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<FileStorage> listImages;

    @JsonIgnore
    @OneToMany(mappedBy = "productDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<Items> listItems;

    @JsonIgnore
    @OneToMany(mappedBy = "productDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<ProductHistory> listProductHistories;

    @JsonIgnore
    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<ProductVariantTemp> listProductVariantTemp;

    @Transient
    Integer availableSalesQty;

    public ProductDetail(int id) {
        super.id = id;
    }

    public int getAvailableSalesQty() {
        return storageQty - defectiveQty;
    }

	@Override
	public String toString() {
		return "ProductVariant [id=" + super.id + ", variantCode=" + variantCode + ", variantName=" + variantName + ", status=" + status;
	}

    public String toStringInsert() {
        return "ProductVariant [id=" + this.id + ", code=" + variantCode + ", name=" + variantName +
                             ", color=" + color.getName() + ", size=" + size.getName() +
                             ", retailPrice=" + retailPrice + ", retailPriceDiscount=" + retailPriceDiscount +
                             ", wholesalePrice=" + wholesalePrice + ", wholesalePriceDiscount=" + wholesalePriceDiscount +
                             ", costPrice=" + costPrice + ", purchasePrice=" + purchasePrice +
                             ", status=" + status;
    }
}