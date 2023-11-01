package com.flowiee.app.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.category.entity.KenhBanHang;
import com.flowiee.app.category.entity.TrangThaiDonHang;
import com.flowiee.app.category.entity.TrangThaiGiaoHang;
import com.flowiee.app.system.entity.Account;
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
public class Order extends BaseEntity implements Serializable {
	@Column(name = "ma_don_hang", length = 20, nullable = false)
	private String maDonHang;

	@Column(name = "receiver_name")
	private String receiverName;

	@Column(name = "receiver_phone")
	private String receiverPhone;

	@Column(name = "receiver_email")
	private String receiverEmail;

	@Column(name = "receiver_address")
	private String receiverAddress;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "khach_hang_id", nullable = false)
	private Customer customer;

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

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderPay> listOrderPay;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trang_thai_don_hang", nullable = false)
	private TrangThaiDonHang trangThaiDonHang;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<TrangThaiGiaoHang> listTrangThaiGiaoHang;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderDetail> listOrderDetail;

	@Override
	public String toString() {
		return "DonHang{" +
				"maDonHang='" + maDonHang + '\'' +
				", khachHang=" + customer +
				", ghiChu='" + ghiChu + '\'' +
				", thoiGianDatHang=" + thoiGianDatHang +
				", tongTienDonHang=" + tongTienDonHang +
				", nhanVienBanHang=" + nhanVienBanHang +
				", kenhBanHang=" + kenhBanHang +
				", trangThaiDonHang=" + trangThaiDonHang +
				'}';
	}
}