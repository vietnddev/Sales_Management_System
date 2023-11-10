package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Entity
@Table(name = "san_pham")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "loai_san_pham", nullable = false)
    private Integer loaiSanPham;

    @Column(name = "ten_san_pham", nullable = false)
    private String tenSanPham;

    @Column(name = "don_vi_tinh", nullable = false)
    private Integer donViTinh;

    @Lob
    @Column(name = "mo_ta_san_pham", length = 30000, nullable = true, columnDefinition = "CLOB")
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
        return "Product{" +
            "id=" + id +
            ", loaiproduct=" + loaiSanPham +
            ", tenSanPham='" + tenSanPham + '\'' +
            ", moTaSanPham='" + moTaSanPham + '\'' +
            ", trangThai=" + trangThai +
            '}';
    }
}