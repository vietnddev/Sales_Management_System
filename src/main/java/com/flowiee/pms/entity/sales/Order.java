package com.flowiee.pms.entity.sales;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.entity.BaseEntity;
import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.FileStorage;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order extends BaseEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "code", length = 20, nullable = false)
	private String code;

	@Column(name = "receiver_name")
	private String receiverName;

	@Column(name = "receiver_phone")
	private String receiverPhone;

	@Column(name = "receiver_email")
	private String receiverEmail;

	@Column(name = "receiver_address")
	private String receiverAddress;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Column(name = "note", length = 500)
	private String note;

	@Column(name = "order_time", nullable = false)
	private LocalDateTime orderTime;

	@Column(name = "voucher_used")
	private String voucherUsedCode;

	@Column(name = "amount_discount")
	private BigDecimal amountDiscount;

	@Column(name = "shipping_cost")
	private BigDecimal shippingCost;

	@Column(name = "is_gift_wrapped")
	private Boolean isGiftWrapped;

	@Column(name = "gift_wrap_cost")
	private BigDecimal giftWrapCost;

	@Column(name = "packaging_cost")
	private BigDecimal packagingCost;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sales")
	private Account nhanVienBanHang;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel", nullable = false)
	private Category kenhBanHang;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "payment_time")
	private LocalDateTime paymentTime;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_method")
	private Category paymentMethod;

	@Column(name = "payment_status")
	private Boolean paymentStatus;

	@Column(name = "payment_note")
	private String paymentNote;

	@Column(name = "payment_amount")
	private Float paymentAmount;

	@Column(name = "cod_fee")
	private BigDecimal codFee;

//	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
//	private List<OrderPay> listOrderPay;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_export_id")
	private TicketExport ticketExport;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status", nullable = false)
	private Category trangThaiDonHang;

	@Column(name = "cancellation_date")
	private LocalDateTime cancellationDate;

	@Column(name = "cancellation_reason")
	private String cancellationReason;

	@JsonIgnore
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderDetail> listOrderDetail;

	@JsonIgnore
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderHistory> listOrderHistory;

	@JsonIgnore
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
		if (!this.getCustomer().getCustomerName().equals(compare.getCustomer().getCustomerName())) {
			map.put("Customer", this.getCustomer().getCustomerName() + "#" + compare.getCustomer().getCustomerName());
		}
		if (!this.getNote().equals(compare.getNote())) {
			map.put("Note", this.getNote() + "#" + compare.getNote());
		}
		if (this.getOrderTime().compareTo(compare.getOrderTime()) != 0) {
			map.put("Order time", this.getOrderTime() + "#" + compare.getOrderTime());
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
		return "Order [id=" + super.id + ", maDonHang=" + code + ", receiverName=" + receiverName + ", receiverPhone=" + receiverPhone
				+ ", receiverEmail=" + receiverEmail + ", receiverAddress=" + receiverAddress + ", customer=" + customer
				+ ", ghiChu=" + note + ", orderTime=" + orderTime
				+ ", voucherUsedCode=" + voucherUsedCode + ", amountDiscount=" + amountDiscount
				+ ", nhanVienBanHang=" + nhanVienBanHang
				+ ", kenhBanHang=" + kenhBanHang + ", trangThaiDonHang=" + trangThaiDonHang + "]";
	}
}