package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Entity
@Table(name = "pro_price")
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

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @OneToMany(mappedBy = "productPrice", fetch = FetchType.LAZY)
    private List<ProductHistory> listProductHistory;

    public Price (Integer id, Double priceSell) {
        super.id = id;
        this.giaBan = priceSell;
    }

	@Override
	public String toString() {
		return "Price [id=" + super.id + ", productVariant=" + productVariant + ", material=" + material + ", giaMua=" + giaMua
				+ ", unitBuy=" + unitBuy + ", giaBan=" + giaBan + ", unitSell=" + unitSell + ", status=" + status + "]";
	}
}