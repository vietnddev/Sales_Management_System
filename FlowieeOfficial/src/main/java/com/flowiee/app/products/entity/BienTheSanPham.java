package com.flowiee.app.products.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.flowiee.app.file.entity.FileEntity;
import com.flowiee.app.products.model.TrangThai;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "san_pham_bien_the")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BienTheSanPham implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @JsonIgnoreProperties("listBienThe")
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
    private TrangThai trangThai;

    @JsonIgnoreProperties("bienTheSanPham")
    @OneToMany(mappedBy = "bienTheSanPham", fetch = FetchType.LAZY)
    private List<ThuocTinhSanPham> listThuocTinh;

    @JsonIgnoreProperties("bienTheSanPham")
    @OneToMany(mappedBy = "bienTheSanPham", fetch = FetchType.LAZY)
    private List<GiaSanPham> listGiaBan;

    @JsonIgnoreProperties("bienTheSanPham")
    @OneToMany(mappedBy = "bienTheSanPham", fetch = FetchType.LAZY)
    private List<DonHangChiTiet> listDonHangChiTiet;
           
    @OneToMany(mappedBy = "productVariant")
    private List<FileEntity> image;
}
