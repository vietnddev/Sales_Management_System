package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "pro_order_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Items extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bien_the_san_pham_id", nullable = false)
    private ProductVariant productVariant;

    @Column(name = "quantity")
    private int soLuong;

    @Column(name = "note")
    private String ghiChu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private OrderCart orderCart;

    @Transient
    private BigDecimal price;

	@Override
	public String toString() {
		return "Items [id=" + super.id + ", productVariant=" + productVariant + ", soLuong=" + soLuong + ", ghiChu=" + ghiChu
				+ ", orderCart=" + orderCart + "]";
	}
}