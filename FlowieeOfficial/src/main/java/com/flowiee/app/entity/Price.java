package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Entity
@Table(name = "price")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Price extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnoreProperties("listGiaBan")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(name = "gia_mua")
    private Double giaMua;

    @Column(name = "unit_buy")
    private String unitBuy;

    @Column(name = "gia_ban")
    private Double giaBan;

    @Column(name = "unit_sell")
    private String unitSell;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai;

    @Override
    public String toString() {
        return "GiaSanPham{" +
                "id=" + id +
                ", bienTheSanPham=" + productVariant +
                ", giaBan=" + giaBan +
                ", trangThai=" + trangThai +
                '}';
    }
}