package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.danhmuc.entity.FabricType;
import com.flowiee.app.danhmuc.entity.LoaiKichCo;
import com.flowiee.app.danhmuc.entity.LoaiMauSac;
import com.flowiee.app.file.entity.FileStorage;
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
public class BienTheSanPham extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_pham_id", nullable = false)
    private SanPham sanPham;
    
    @Column(name = "ma_san_pham", length = 50, nullable = false)
    private String maSanPham;
    
    @Column(name = "ten_bien_the")
    private String tenBienThe;

    @Column(name = "so_luong_kho", nullable = false)
    private int soLuongKho;

    @Column(name = "da_ban", nullable = false)
    private int soLuongDaBan;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mau_sac_id", nullable = false)
    private LoaiMauSac loaiMauSac;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kich_co_id", nullable = false)
    private LoaiKichCo loaiKichCo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_lieu_vai_id", nullable = false)
    private FabricType fabricType;

    @Transient
    private GiaSanPham giaSanPham;

    @OneToMany(mappedBy = "bienTheSanPham", fetch = FetchType.LAZY)
    private List<ThuocTinhSanPham> listThuocTinh;

    @OneToMany(mappedBy = "bienTheSanPham", fetch = FetchType.LAZY)
    private List<GiaSanPham> listGiaBan;

    @OneToMany(mappedBy = "bienTheSanPham", fetch = FetchType.LAZY)
    private List<DonHangChiTiet> listDonHangChiTiet;

    @OneToMany(mappedBy = "bienTheSanPham", fetch = FetchType.LAZY)
    private List<FileStorage> listFileStorage;

    @OneToMany(mappedBy = "bienTheSanPham", fetch = FetchType.LAZY)
    private List<Items> listItems;

    @OneToMany(mappedBy = "bienTheSanPham", fetch = FetchType.LAZY)
    private List<SanPhamBienTheImport> listProductVariantImport;

    @Override
    public String toString() {
        return "BienTheSanPham{" +
                "sanPham=" + sanPham +
                ", maSanPham='" + maSanPham + '\'' +
                ", tenBienThe='" + tenBienThe + '\'' +
                ", soLuongKho=" + soLuongKho +
                ", trangThai='" + trangThai + '\'' +
                ", loaiMauSac=" + loaiMauSac +
                ", loaiKichCo=" + loaiKichCo +
                ", giaSanPham=" + giaSanPham +
                '}';
    }
}