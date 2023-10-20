package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "don_hang_chi_tiet")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DonHangChiTiet extends BaseEntity implements java.io.Serializable {

	@JsonIgnoreProperties("listDonHangChiTiet")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "don_hang_id", nullable = false)
	private DonHang donHang;

	@JsonIgnoreProperties("listDonHangChiTiet")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bien_the_san_pham_id", nullable = false)
	private BienTheSanPham bienTheSanPham;

	@Column(name = "so_luong", nullable = false)
	private int soLuong;

	@Column(name = "ghi_chu", length = 500)
	private String ghiChu;

	@Column(name = "trang_thai", nullable = false)
	private boolean trangThai;

	@Override
	public String toString() {
		return "DonHangChiTiet{" +
				"donHang=" + donHang +
				", bienTheSanPham=" + bienTheSanPham +
				", soLuong=" + soLuong +
				", ghiChu='" + ghiChu + '\'' +
				", trangThai=" + trangThai +
				'}';
	}
}