package com.flowiee.pms.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.entity.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "product_attribute")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAttribute extends BaseEntity implements Serializable {
    @Serial
	static final long serialVersionUID = 1L;

	@JsonIgnoreProperties("listThuocTinh")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    ProductDetail productDetail;

    @Column(name = "attribute_name", nullable = false)
    String attributeName;

    @Column(name = "attribute_value", length = 500)
    String attributeValue;

    @Column(name = "sort", nullable = false)
    int sort;

    @Column(name = "status", nullable = false)
    boolean status;

    @OneToMany(mappedBy = "productAttribute", fetch = FetchType.LAZY)
    List<ProductHistory> listProductHistory;

    public ProductAttribute(Long id) {
        this.id = id;
    }

	@Override
	public String toString() {
		return "ProductAttribute [id=" + super.id + ", productVariant=" + productDetail + ", tenThuocTinh=" + attributeName
				+ ", giaTriThuocTinh=" + attributeValue + ", sort=" + sort + ", trangThai=" + status + "]";
	}
}