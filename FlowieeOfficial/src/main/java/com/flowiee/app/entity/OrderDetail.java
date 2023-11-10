package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "don_hang_chi_tiet")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderDetail extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnoreProperties("listDonHangChiTiet")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "don_hang_id", nullable = false)
	private Order order;

	@JsonIgnoreProperties("listDonHangChiTiet")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bien_the_san_pham_id", nullable = false)
	private ProductVariant productVariant;

	@Column(name = "so_luong", nullable = false)
	private int soLuong;

	@Column(name = "voucher_code", length = 50)
	private String voucherCode;

	@Column(name = "ghi_chu", length = 500)
	private String ghiChu;

	@Column(name = "trang_thai", nullable = false)
	private boolean trangThai;

	@Override
	public String toString() {
		return "DonHangChiTiet{" +
				"donHang=" + order +
				", bienTheSanPham=" + productVariant +
				", soLuong=" + soLuong +
				", ghiChu='" + ghiChu + '\'' +
				", trangThai=" + trangThai +
				'}';
	}
}