package com.flowiee.app.products.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "san_pham_thuoc_tinh")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ThuocTinhSanPham implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @JsonIgnoreProperties("listThuocTinh")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bien_the_id", nullable = false)
    private BienTheSanPham bienTheSanPham;

    @Column(name = "ten_thuoc_tinh", length = 255, nullable = false)
    private String tenThuocTinh;

    @Column(name = "gia_tri_thuoc_tinh", length = 500, nullable = true)
    private String giaTriThuocTinh;

    @Column(name = "thu_tu_hien_thi", nullable = false)
    private int thuTuHienThi;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai;
}
