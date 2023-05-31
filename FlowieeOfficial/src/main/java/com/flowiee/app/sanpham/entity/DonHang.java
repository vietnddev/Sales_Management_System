package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.danhmuc.entity.KenhBanHang;
import com.flowiee.app.hethong.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "don_hang")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DonHang implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "ma_don_hang", length = 20, nullable = false)
	private String maDonHang;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "khach_hang_id", nullable = false)
	private KhachHang khachHang;

	@Column(name = "ghi_chu", length = 500)
	private String ghiChu;

	@Column(name = "thoi_gian_dat_hang", nullable = false)
	private Date thoiGianDatHang;

	@Column(name = "tong_tien_don_hang")
	private Double tongTienDonHang;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nhan_vien_ban_hang")
	private Account nhanVienBanHang;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kenh_ban_hang", nullable = false)
	private KenhBanHang kenhBanHang;

	@Column(name = "trang_thai")
	private String trangThai;

	@JsonIgnoreProperties("donHang")
	@OneToMany(mappedBy = "donHang", fetch = FetchType.LAZY)
	private List<DonHangChiTiet> listDonHangChiTiet;
}
