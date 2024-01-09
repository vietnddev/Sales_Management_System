package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Table(name = "pro_order_detail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderDetail extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnoreProperties("listDonHangChiTiet")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@JsonIgnoreProperties("listDonHangChiTiet")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_variant_id", nullable = false)
	private ProductVariant productVariant;

	@Column(name = "quantity", nullable = false)
	private int soLuong;

	@Column(name = "price", nullable = false)
	private Float price;

	@Column(name = "price_original", nullable = false)
	private Float priceOriginal;

	@Column(name = "note", length = 500)
	private String ghiChu;

	@Column(name = "status", nullable = false)
	private boolean trangThai;

	@OneToMany(mappedBy = "orderDetail", fetch = FetchType.LAZY)
	private List<OrderHistory> listOrderHistory;

	@Override
	public String toString() {
		return "OrderDetail [id=" + super.id + ", order=" + order + ", productVariant=" + productVariant + ", soLuong=" + soLuong
				+ ", ghiChu=" + ghiChu + ", trangThai=" + trangThai + ", listOrderHistory=" + listOrderHistory + "]";
	}
}