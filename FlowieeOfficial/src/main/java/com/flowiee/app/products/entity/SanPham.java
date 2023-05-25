package com.flowiee.app.products.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.danhmuc.entity.LoaiSanPham;
import com.flowiee.app.file.entity.FileStorage;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "san_pham")
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SanPham implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private  int id;

    @JsonIgnoreProperties("listSanPham")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loai_san_pham", nullable = false, insertable = true, updatable = true)
    private LoaiSanPham loaiSanPham;

    @Column(name = "ten_san_pham", length = 255, nullable = false)
    private String tenSanPham;

    @Lob
    @Column(name = "mo_ta_san_pham", length = 30000, nullable = true, columnDefinition = "CLOB")
    private String moTaSanPham;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai;

    @JsonIgnoreProperties("sanPham")
    @OneToMany(mappedBy = "sanPham", fetch = FetchType.LAZY)
    private List<BienTheSanPham> listBienThe;

    @JsonIgnoreProperties("sanPham")
    @OneToMany(mappedBy = "sanPham", fetch = FetchType.LAZY)
    private List<FileStorage> listFileStorage;
}