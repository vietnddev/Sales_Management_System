package com.flowiee.sms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.flowiee.sms.core.BaseEntity;

import com.flowiee.sms.model.dto.ProductVariantDTO;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Entity
@Table(name = "pro_product_detail")
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

    @Column(name = "quantity_stg", nullable = false)
    private Integer storageQty;

    @Column(name = "quantity_sell", nullable = false)
    private Integer soldQty;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_import_id")
    private TicketImport ticketImport;

    @Column(name = "original_price", nullable = false)
    private BigDecimal originalPrice;

    @Column(name = "discount_price", nullable = false)
    private BigDecimal discountPrice;

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

    public ProductDetail(int id) {
        super.id = id;
    }

    public static ProductDetail fromProductVariantDTO(ProductVariantDTO dto) {
        ProductDetail p = new ProductDetail();
        p.setId(dto.getId());
        p.setProduct(new Product(dto.getProductId()));
        p.setVariantName(dto.getVariantName());
        p.setFabricType(new Category(dto.getFabricTypeId(), dto.getFabricTypeName()));
        p.setColor(new Category(dto.getColorId(), dto.getColorName()));
        p.setSize(new Category(dto.getSizeId(), dto.getSizeName()));
        p.setOriginalPrice(dto.getOriginalPrice());
        p.setDiscountPrice(dto.getDiscountPrice());
        return p;
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