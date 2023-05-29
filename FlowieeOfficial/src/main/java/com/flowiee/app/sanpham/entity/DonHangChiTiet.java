package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "don_hang_chi_tiet")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DonHangChiTiet implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

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
}