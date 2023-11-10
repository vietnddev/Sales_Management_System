package com.flowiee.app.category.entity;

import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.entity.product.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dm_trang_thai_don_hang")
public class TrangThaiDonHang extends BaseEntity implements Serializable {
    @Column(name = "ten", unique = true, nullable = false)
    private String ten;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai;

    @OneToMany(mappedBy = "trangThaiDonHang", fetch = FetchType.LAZY)
    private List<Order> listOrder;

    public TrangThaiDonHang (int id) {
        super.id = id;
    }
}