package com.flowiee.app.danhmuc.entity;

import com.flowiee.app.common.entity.BaseEntity;
import com.flowiee.app.sanpham.entity.DonHang;
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
    private List<DonHang> listDonHang;

    public TrangThaiDonHang (int id) {
        super.id = id;
    }
}