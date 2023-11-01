package com.flowiee.app.category.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.product.entity.Order;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_kenh_ban_hang")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@AllArgsConstructor
public class KenhBanHang extends BaseEntity implements Serializable {
    @Column(name = "ma_loai", length = 50)
    private String maLoai;

    @Column(name = "ten_loai", nullable = false)
    private String tenLoai;

    @Column(name = "mau_nhan")
    private String mauNhan;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai;

    @OneToMany(mappedBy = "kenhBanHang", fetch = FetchType.LAZY)
    private List<Order> listOrder;

    public KenhBanHang (int id) {
        super.id = id;
    }

    @Override
    public String toString() {
        return "KenhBanHang {id=" + id + ", maLoai=" + maLoai +", tenLoai=" + tenLoai + ", ghiChu=" + ghiChu + ", trangThai=" + trangThai + "}";
    }
}