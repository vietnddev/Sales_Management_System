package com.flowiee.app.sanpham.entity;

import com.flowiee.app.danhmuc.entity.HinhThucThanhToan;
import com.flowiee.app.danhmuc.entity.KenhBanHang;
import com.flowiee.app.hethong.entity.Account;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "don_hang_thanh_toan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonHangThanhToan implements Serializable {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "don_hang_id", nullable = false)
    private DonHang donHang;

    @Column(name = "ma_phieu", nullable = false)
    private String maPhieu;

    @Column(name = "thoi_gian_thanh_toan", nullable = false)
    private Date thoiGianThanhToan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hinh_thuc_thanh_toan", nullable = false)
    private HinhThucThanhToan hinhThucThanhToan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nhan_vien", nullable = false)
    private Account thuNgan;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai_thanh_toan", nullable = false)
    private boolean trangThaiThanhToan;
}