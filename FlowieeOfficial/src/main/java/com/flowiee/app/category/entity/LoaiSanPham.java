package com.flowiee.app.category.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.entity.Product;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_loai_san_pham")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@AllArgsConstructor
public class LoaiSanPham extends BaseEntity implements Serializable {
    @Column(name = "ma_loai", length = 50)
    private String maLoai;

    @NotBlank(message = "Tên loại sản phẩm không được để trống")
    @Column(name = "ten_loai", nullable = false)
    private String tenLoai;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai;

//    @JsonIgnoreProperties("loaiSanPham")
//    @OneToMany(mappedBy = "loaiSanPham", fetch = FetchType.LAZY)
//    private List<Product> listProduct;

    @Override
    public String toString() {
        return "LoaiSanPham {id=" + id + ", maLoai=" + maLoai +", tenLoai=" + tenLoai + ", ghiChu=" + ghiChu + ", trangThai=" + trangThai + "}";
    }
}