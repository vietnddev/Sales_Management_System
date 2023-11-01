package com.flowiee.app.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.category.entity.DonViTinh;
import com.flowiee.app.category.entity.LoaiSanPham;
import com.flowiee.app.storage.entity.FileStorage;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Entity
@Table(name = "san_pham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loai_san_pham", nullable = false)
    private LoaiSanPham loaiSanPham;

    @Column(name = "ten_san_pham", nullable = false)
    private String tenSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "don_vi_tinh", nullable = false)
    private DonViTinh donViTinh;

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