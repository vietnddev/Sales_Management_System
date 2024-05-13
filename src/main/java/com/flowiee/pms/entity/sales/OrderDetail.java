package com.flowiee.pms.entity.sales;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.entity.BaseEntity;
import com.flowiee.pms.entity.product.ProductDetail;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Entity
@Table(name = "order_detail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderDetail extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@JsonIgnoreProperties("listDonHangChiTiet")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@JsonIgnore
	@JsonIgnoreProperties("listDonHangChiTiet")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_variant_id", nullable = false)
	private ProductDetail productDetail;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "price_original", nullable = false)
	private BigDecimal priceOriginal;

	@Column(name = "extra_discount")
	private BigDecimal extraDiscount;

	@Column(name = "note", length = 500)
	private String note;

	@Column(name = "status", nullable = false)
	private boolean status;

	@OneToMany(mappedBy = "orderDetail", fetch = FetchType.LAZY)
	private List<OrderHistory> listOrderHistory;

	public OrderDetail(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "OrderDetail [id=" + super.id + ", order=" + order + ", productVariant=" + productDetail + ", soLuong=" + quantity
				+ ", ghiChu=" + note + ", status=" + status + ", listOrderHistory=" + listOrderHistory + "]";
	}
}