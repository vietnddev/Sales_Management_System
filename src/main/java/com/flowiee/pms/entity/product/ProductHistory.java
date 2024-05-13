package com.flowiee.pms.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "product_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductHistory extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id")
    private ProductDetail productDetail;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_attribute_id")
    private ProductAttribute productAttribute;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "field", nullable = false)
    private String field;

    @Lob
    @Column(name = "old_value", nullable = false, length = 9999, columnDefinition = "CLOB")
    private String oldValue;

    @Lob
    @Column(name = "new_value", nullable = false, length = 9999, columnDefinition = "CLOB")
    private String newValue;

    @Transient
    private Integer productId;

    @Transient
    private Integer productVariantId;

    @Transient
    private Integer productAttributeId;

    public ProductHistory(Integer productId, Integer variantId, Integer attributeId, String title, String field, String oldValue, String newValue) {
        if (productId != null) {
            this.product = new Product(productId);
        }
        if (variantId != null) {
            this.productDetail = new ProductDetail(variantId);
        }
        if (attributeId != null) {
            this.productAttribute = new ProductAttribute(attributeId);
        }
        this.title = title;
        this.field = field;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

	@Override
	public String toString() {
		return "ProductHistory [id=" + super.id + ", title=" + title + ", fieldName=" + field + ", oldValue=" + oldValue + ", newValue=" + newValue + "]";
	}
}