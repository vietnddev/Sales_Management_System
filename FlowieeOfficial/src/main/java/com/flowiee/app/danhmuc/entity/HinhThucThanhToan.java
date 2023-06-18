package com.flowiee.app.danhmuc.entity;

import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.entity.DonHangThanhToan;
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
public class HinhThucThanhToan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "ma_loai", length = 50)
    private String maLoai;

    @Column(name = "ten_loai", nullable = false)
    private String tenLoai;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai;

    @OneToMany(mappedBy = "hinhThucThanhToan", fetch = FetchType.LAZY)
    private List<DonHangThanhToan> listThanhToan;

    @Override
    public String toString() {
        return "HinhThucThanhToan{" +
                "id=" + id +
                ", maLoai='" + maLoai + '\'' +
                ", tenLoai='" + tenLoai + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}
