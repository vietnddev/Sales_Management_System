package com.flowiee.app.category.entity;

import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.product.entity.OrderPay;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dm_hinh_thuc_thanh_toan")
public class HinhThucThanhToan extends BaseEntity implements Serializable {
    @Column(name = "ma_loai", length = 50)
    private String maLoai;

    @Column(name = "ten_loai", nullable = false)
    private String tenLoai;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai;

    @OneToMany(mappedBy = "hinhThucThanhToan", fetch = FetchType.LAZY)
    private List<OrderPay> listThanhToan;

    @Override
    public String toString() {
        return "HinhThucThanhToan {id=" + id + ", maLoai=" + maLoai +", tenLoai=" + tenLoai + ", ghiChu=" + ghiChu + ", trangThai=" + trangThai + "}";
    }

    public HinhThucThanhToan (Integer id, String name) {
        super.id = id;
        this.tenLoai = name;
    }
}
