package com.flowiee.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flowiee.app.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OrderDTO implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

    private Integer orderId;
    private String orderCode;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date orderTime;
    private String orderTimeStr;
    private String receiveAddress;
    private String receivePhone;
	private String receiveEmail;
    private String receiveName;
    private Integer customerId;
    private String customerName;
    private Integer salesChannelId;
    private String salesChannelName;
    private Integer orderStatusId;
    private String orderStatusName;
    private Integer payMethodId;
    private String payMethodName;
    private Integer cashierId;
    private String cashierName;
    private Integer createdById;
    private String createdByName;
    private Date createdAt;
	private String createdAtStr;
	private Double amountDiscount;
	private Double totalAmount;
	private Double totalAmountDiscount;
	private Integer totalProduct;
    private String qrCode;
	private String voucherUsedCode;
	private boolean paymentStatus;
	private Date paymentTime;
	private String paymentTimeStr;
	private Float paymentAmount;
	private String paymentNote;
	private Integer cartId;
	private String note;
	private Integer ticketExportId;
	//@JsonIgnore
	//private List<OrderDetail> listOrderDetail;
	private List<OrderDetailDTO> listOrderDetailDTO;

	public static OrderDTO fromOrder(Order order) {
		OrderDTO dto = new OrderDTO();
		dto.setOrderId(order.getId());
		dto.setOrderCode(order.getMaDonHang());
		dto.setOrderTime(order.getThoiGianDatHang());
		//dto.setOrderTimeStr(DateUtils.convertDateToString("EEE MMM dd HH:mm:ss zzz yyyy", "dd/MM/yyyy HH:mm:ss", order.getThoiGianDatHang()));
		dto.setReceiveAddress(order.getReceiverAddress());
		dto.setReceivePhone(order.getReceiverPhone());
		dto.setReceiveEmail(order.getReceiverEmail());
		dto.setReceiveName(order.getReceiverName());
		dto.setCustomerId(order.getCustomer().getId());
		dto.setCustomerName(order.getCustomer().getTenKhachHang());
		dto.setSalesChannelId(order.getKenhBanHang().getId());
		dto.setSalesChannelName(order.getKenhBanHang().getName());
		dto.setOrderStatusId(order.getTrangThaiDonHang().getId());
		dto.setOrderStatusName(order.getTrangThaiDonHang().getName());
		if (order.getPaymentMethod() != null) {
			dto.setPayMethodId(order.getPaymentMethod().getId());
			dto.setPayMethodName(order.getPaymentMethod().getName());
		}
		dto.setCashierId(order.getNhanVienBanHang().getId());
		dto.setCashierName(order.getNhanVienBanHang().getHoTen());
		dto.setCreatedById(order.getCreatedBy());
		dto.setCreatedByName(null);
		dto.setCreatedAt(order.getCreatedAt());
		//dto.setCreatedAtStr(DateUtils.convertDateToString("EEE MMM dd HH:mm:ss zzz yyyy", "dd/MM/yyyy HH:mm:ss", order.getCreatedAt()));
		dto.setAmountDiscount(order.getAmountDiscount() != null ? order.getAmountDiscount() : 0);
		//dto.setTotalAmount(null);
		if (order.getListOrderDetail() != null) {
			double totalAmtDiscount = 0;
			for (OrderDetail d : order.getListOrderDetail()) {
				totalAmtDiscount += d.getPrice();
			}
			dto.setTotalAmountDiscount(totalAmtDiscount - dto.getAmountDiscount());
		}
		//dto.setTotalAmountDiscount(null);
		//dto.setTotalProduct(null);
		if (!order.getListImageQR().isEmpty() && order.getListImageQR().get(0) != null) {
			FileStorage imageQRCode = order.getListImageQR().get(0);
			dto.setQrCode(imageQRCode.getDirectoryPath() + "/" + imageQRCode.getTenFileKhiLuu());
		}
		dto.setVoucherUsedCode(order.getVoucherUsedCode());
		dto.setPaymentStatus((order.getPaymentStatus() == null || !order.getPaymentStatus()) ? false : true);
		dto.setPaymentTime(order.getPaymentTime());
		//dto.setPaymentTimeStr(DateUtils.convertDateToString("EEE MMM dd HH:mm:ss zzz yyyy", "dd/MM/yyyy HH:mm:ss", order.getPaymentTime()));
		dto.setPaymentAmount(order.getPaymentAmount());
		dto.setPaymentNote(order.getPaymentNote());
		dto.setCartId(null);
		dto.setNote(order.getGhiChu());
		if (order.getTicketExport() != null) {
			dto.setTicketExportId(order.getTicketExport().getId());
		}
		//dto.setListOrderDetail(order.getListOrderDetail());
		dto.setListOrderDetailDTO(OrderDetailDTO.fromOrderDetails(order.getListOrderDetail()));
		return dto;
	}

	public static List<OrderDTO> fromOrders(List<Order> orders) {
		List<OrderDTO> list = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(orders)) {
			for (Order p : orders) {
				list.add(OrderDTO.fromOrder(p));
			}
		}
		return list;
	}
}