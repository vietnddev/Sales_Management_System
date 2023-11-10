package com.flowiee.app.entity.product;

import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "don_hang_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Items extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bien_the_san_pham_id", nullable = false)
    private ProductVariant productVariant;

    @Column(name = "so_luong")
    private int soLuong;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private OrderCart orderCart;

    @Override
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", bienTheSanPham=" + productVariant +
                ", soLuong=" + soLuong +
                ", GhiChu='" + ghiChu + '\'' +
                ", cart=" + orderCart +
                '}';
    }
}