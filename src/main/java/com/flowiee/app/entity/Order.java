package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pro_don_hang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

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
	@JoinColumn(name = "customer_id", nullable = false)
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
	private Category kenhBanHang;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderPay> listOrderPay;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trang_thai_don_hang", nullable = false)
	private Category trangThaiDonHang;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderDetail> listOrderDetail;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderHistory> listOrderHistory;

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