package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.flowiee.app.danhmuc.entity.LoaiKichCo;
import com.flowiee.app.danhmuc.entity.LoaiMauSac;
import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.sanpham.model.TrangThai;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Entity
@Table(name = "san_pham_bien_the")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BienTheSanPham implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_pham_id", nullable = false)
    private SanPham sanPham;
    
    @Column(name = "ma_san_pham", length = 50, nullable = false)
    private String maSanPham;
    
    @Column(name = "loai_bien_the")
    private String loaiBienThe;
    
    @Column(name = "ten_bien_the")
    private String tenBienThe;
    
    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    @OneToMany(mappedBy = "bienTheSanPham", fetch = FetchType.LAZY)
    private List<ThuocTinhSanPham> listThuocTinh;

    @OneToMany(mappedBy = "bienTheSanPham", fetch = FetchType.LAZY)
    private List<GiaSanPham> listGiaBan;

    @OneToMany(mappedBy = "bienTheSanPham", fetch = FetchType.LAZY)
    private List<DonHangChiTiet> listDonHangChiTiet;

    @OneToMany(mappedBy = "bienTheSanPham", fetch = FetchType.LAZY)
    private List<FileStorage> listFileStorage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mau_sac_id", nullable = false)
    private LoaiMauSac loaiMauSac;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kich_co_id", nullable = false)
    private LoaiKichCo loaiKichCo;
}