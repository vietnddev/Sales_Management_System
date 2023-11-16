package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;

import com.flowiee.app.category.Category;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Entity
@Table(name = "pro_san_pham")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id", nullable = false)
    private Category productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Category brand;

    @Column(name = "ten_san_pham", nullable = false)
    private String tenSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Category unit;

    @Lob
    @Column(name = "mo_ta_san_pham", length = 30000, columnDefinition = "TEXT")
    private String moTaSanPham;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai;

    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductVariant> listBienThe;

    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<FileStorage> listFileStorage;

    @Transient
    private FileStorage imageActive;

    public Product(int id) {
        super.id = id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Product {");
        builder.append("id=").append(id).append(", ");
        builder.append("name=").append(tenSanPham);
        builder.append("}");
        return builder.toString();
    }
}