package com.flowiee.app.danhmuc.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.entity.DonHangChiTiet;
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
public class TrangThaiDonHang implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "ten", unique = true, nullable = false)
    private String ten;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai;

    @OneToMany(mappedBy = "trangThaiDonHang", fetch = FetchType.LAZY)
    private List<DonHang> listDonHang;
}