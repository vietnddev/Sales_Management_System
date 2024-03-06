package com.flowiee.sms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.sms.core.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "pro_product_attribute")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductAttribute extends BaseEntity implements Serializable {
    @Serial
	private static final long serialVersionUID = 1L;

	@JsonIgnoreProperties("listThuocTinh")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductDetail productDetail;

    @Column(name = "attribute_name", nullable = false)
    private String attributeName;

    @Column(name = "attribute_value", length = 500)
    private String attributeValue;

    @Column(name = "sort", nullable = false)
    private int sort;

    @Column(name = "status", nullable = false)
    private boolean trangThai;

    @OneToMany(mappedBy = "productAttribute", fetch = FetchType.LAZY)
    private List<ProductHistory> listProductHistory;

    public ProductAttribute(Integer id) {
        this.id = id;
    }

    public Map<String, String> compareTo(ProductAttribute entityToCompare) {
        Map<String, String> map = new HashMap<>();
        if (!this.getAttributeName().equals(entityToCompare.getAttributeName())) {
            map.put("Product attribute name", this.getAttributeName() + "#" + entityToCompare.getAttributeName());
        }
        if (!this.getAttributeValue().equals(entityToCompare.getAttributeValue())) {
            map.put("Product attribute value", this.getAttributeValue() + "#" + entityToCompare.getAttributeValue());
        }
        return map;
    }

	@Override
	public String toString() {
		return "ProductAttribute [id=" + super.id + ", productVariant=" + productDetail + ", tenThuocTinh=" + attributeName
				+ ", giaTriThuocTinh=" + attributeValue + ", sort=" + sort + ", trangThai=" + trangThai + "]";
	}    
}