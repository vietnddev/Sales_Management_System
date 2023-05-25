package com.flowiee.app.danhmuc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.products.entity.DonHangChiTiet;
import com.flowiee.app.products.entity.SanPham;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dm_loai_san_pham")
public class LoaiSanPham implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "ma_loai", length = 50, nullable = true)
    private String maLoai;

    @NotBlank(message = "Tên loại sản phẩm không được để trống")
    @Column(name = "ten_loai", length = 255, nullable = false)
    private String tenLoai;

    @Column(name = "ghi_chu", length = 255, nullable = true)
    private String ghiChu;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai;

    @JsonIgnoreProperties("loaiSanPham")
    @OneToMany(mappedBy = "loaiSanPham", fetch = FetchType.LAZY)
    private List<SanPham> listSanPham;
}