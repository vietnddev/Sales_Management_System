package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.common.entity.BaseEntity;
import com.flowiee.app.danhmuc.entity.KenhBanHang;
import com.flowiee.app.danhmuc.entity.TrangThaiDonHang;
import com.flowiee.app.danhmuc.entity.TrangThaiGiaoHang;
import com.flowiee.app.hethong.entity.Account;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "don_hang")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DonHang extends BaseEntity implements Serializable {
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

	@OneToMany(mappedBy = "donHang", fetch = FetchType.LAZY)
	private List<DonHangThanhToan> listDonHangThanhToan;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trang_thai_don_hang", nullable = false)
	private TrangThaiDonHang trangThaiDonHang;

	@OneToMany(mappedBy = "donHang", fetch = FetchType.LAZY)
	private List<TrangThaiGiaoHang> listTrangThaiGiaoHang;

	@OneToMany(mappedBy = "donHang", fetch = FetchType.LAZY)
	private List<DonHangChiTiet> listDonHangChiTiet;

	@Override
	public String toString() {
		return "DonHang{" +
				"maDonHang='" + maDonHang + '\'' +
				", khachHang=" + khachHang +
				", ghiChu='" + ghiChu + '\'' +
				", thoiGianDatHang=" + thoiGianDatHang +
				", tongTienDonHang=" + tongTienDonHang +
				", nhanVienBanHang=" + nhanVienBanHang +
				", kenhBanHang=" + kenhBanHang +
				", trangThaiDonHang=" + trangThaiDonHang +
				'}';
	}
}
