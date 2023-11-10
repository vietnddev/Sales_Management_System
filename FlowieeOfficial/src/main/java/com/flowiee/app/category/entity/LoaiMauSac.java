package com.flowiee.app.category.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.entity.ProductVariant;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_loai_mau_sac")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@AllArgsConstructor
public class LoaiMauSac extends BaseEntity implements Serializable {
    @Column(name = "ma_loai", length = 50)
    private String maLoai;

    @Column(name = "ten_loai", nullable = false)
    private String tenLoai;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai;

    @OneToMany(mappedBy = "loaiMauSac", fetch = FetchType.LAZY)
    private List<ProductVariant> listProductVariant;

    @Override
    public String toString() {
        return "LoaiMauSac {id=" + id + ", maLoai=" + maLoai +", tenLoai=" + tenLoai + ", ghiChu=" + ghiChu + ", trangThai=" + trangThai + "}";
    }
}