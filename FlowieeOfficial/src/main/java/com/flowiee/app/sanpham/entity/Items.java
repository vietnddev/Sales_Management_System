package com.flowiee.app.sanpham.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "don_hang_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Items implements Serializable {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bien_the_san_pham_id", nullable = false)
    private BienTheSanPham bienTheSanPham;

    @Column(name = "so_luong")
    private int soLuong;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Override
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", bienTheSanPham=" + bienTheSanPham +
                ", soLuong=" + soLuong +
                ", GhiChu='" + ghiChu + '\'' +
                ", cart=" + cart +
                '}';
    }
}