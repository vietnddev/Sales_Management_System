package com.flowiee.app.danhmuc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.entity.SanPham;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
@Table(name = "dm_don_vi_tinh")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@AllArgsConstructor
public class DonViTinh implements Serializable {
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

    @OneToMany(mappedBy = "donViTinh", fetch = FetchType.LAZY)
    private List<SanPham> listSanPham;

    @Override
    public String toString() {
        return "KenhBanHang{" +
            "id=" + id +
            ", maLoai='" + maLoai + '\'' +
            ", tenLoai='" + tenLoai + '\'' +
            ", ghiChu='" + ghiChu + '\'' +
            ", trangThai=" + trangThai +
            '}';
    }
}