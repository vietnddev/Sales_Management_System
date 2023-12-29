package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "pro_order")
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

	@Column(name = "total_amount")
	private Double totalAmount;
	
	@Column(name = "voucher_used_code")
	private String voucherUsedCode;
	
	@Column(name = "amount_discount")
	private Double amountDiscount;
	
	@Column(name = "total_amount_after_discount")
	private Double totalAmountAfterDiscount;

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

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<FileStorage> listImageQR;
	public Order(int id) {
		super.id = id;
	}
	public Map<String, String> compareTo(Order compare) {
		Map<String, String> map = new HashMap<>();
		if (!this.getReceiverName().equals(compare.getReceiverName())) {
			map.put("Receiver name", this.getReceiverName() + "#" + compare.getReceiverName());
		}
		if (!this.getReceiverPhone().equals(compare.getReceiverPhone())) {
			map.put("Receiver phone", this.getReceiverPhone() + "#" + compare.getReceiverPhone());
		}
		if (!this.getReceiverAddress().equals(compare.getReceiverAddress())) {
			map.put("Receiver address", this.getReceiverAddress() + "#" + compare.getReceiverAddress());
		}
		if (!this.getReceiverEmail().equals(compare.getReceiverEmail())) {
			map.put("Receiver email", this.getReceiverEmail() + "#" + compare.getReceiverEmail());
		}
		if (!this.getCustomer().getTenKhachHang().equals(compare.getCustomer().getTenKhachHang())) {
			map.put("Customer", this.getCustomer().getTenKhachHang() + "#" + compare.getCustomer().getTenKhachHang());
		}
		if (!this.getGhiChu().equals(compare.getGhiChu())) {
			map.put("Note", this.getGhiChu() + "#" + compare.getGhiChu());
		}
		if (this.getThoiGianDatHang().compareTo(compare.getThoiGianDatHang()) != 0) {
			map.put("Order time", this.getThoiGianDatHang() + "#" + compare.getThoiGianDatHang());
		}
		if (!this.getVoucherUsedCode().equals(compare.getVoucherUsedCode())) {
			map.put("Voucher", this.getVoucherUsedCode() + "#" + compare.getVoucherUsedCode());
		}
		if (!this.getKenhBanHang().getName().equals(compare.getKenhBanHang().getName())) {
			map.put("Sales channel", this.getKenhBanHang().getName() + "#" + compare.getKenhBanHang().getName());
		}
		if (!this.getTrangThaiDonHang().getName().equals(compare.getTrangThaiDonHang().getName())) {
			map.put("Order status", this.getTrangThaiDonHang().getName() + "#" + compare.getTrangThaiDonHang().getName());
		}
		return map;
	}

	@Override
	public String toString() {
		return "Order [id=" + super.id + ", maDonHang=" + maDonHang + ", receiverName=" + receiverName + ", receiverPhone=" + receiverPhone
				+ ", receiverEmail=" + receiverEmail + ", receiverAddress=" + receiverAddress + ", customer=" + customer
				+ ", ghiChu=" + ghiChu + ", thoiGianDatHang=" + thoiGianDatHang + ", totalAmount=" + totalAmount
				+ ", voucherUsedCode=" + voucherUsedCode + ", amountDiscount=" + amountDiscount
				+ ", totalAmountAfterDiscount=" + totalAmountAfterDiscount + ", nhanVienBanHang=" + nhanVienBanHang
				+ ", kenhBanHang=" + kenhBanHang + ", trangThaiDonHang=" + trangThaiDonHang + "]";
	}
}