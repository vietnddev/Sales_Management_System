package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.entity.sales.OrderDetail;
import com.flowiee.pms.entity.system.FileStorage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OrderDTO extends Order implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

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
	private BigDecimal totalAmount;
	private BigDecimal totalAmountDiscount;
	private Integer totalProduct;
    private String qrCode;
	private Integer cartId;
	private Integer ticketExportId;
	private List<OrderDetailDTO> listOrderDetailDTO;

	public static OrderDTO fromOrder(Order order) {
		OrderDTO dto = new OrderDTO();
		dto.setId(order.getId());
		dto.setCode(order.getCode());
		dto.setOrderTime(order.getOrderTime());
		//dto.setOrderTimeStr(DateUtils.convertDateToString("EEE MMM dd HH:mm:ss zzz yyyy", "dd/MM/yyyy HH:mm:ss", order.getThoiGianDatHang()));
		dto.setReceiverAddress(order.getReceiverAddress());
		dto.setReceiverPhone(order.getReceiverPhone());
		dto.setReceiverEmail(order.getReceiverEmail());
		dto.setReceiverName(order.getReceiverName());
		dto.setCustomerId(order.getCustomer().getId());
		dto.setCustomerName(order.getCustomer().getCustomerName());
		dto.setSalesChannelId(order.getKenhBanHang().getId());
		dto.setSalesChannelName(order.getKenhBanHang().getName());
		dto.setOrderStatusId(order.getTrangThaiDonHang().getId());
		dto.setOrderStatusName(order.getTrangThaiDonHang().getName());
		if (order.getPaymentMethod() != null) {
			dto.setPayMethodId(order.getPaymentMethod().getId());
			dto.setPayMethodName(order.getPaymentMethod().getName());
		}
		dto.setCashierId(order.getNhanVienBanHang().getId());
		dto.setCashierName(order.getNhanVienBanHang().getFullName());
		dto.setCreatedById(order.getCreatedBy());
		dto.setCreatedByName(null);
		dto.setCreatedAt(order.getCreatedAt());
		//dto.setCreatedAtStr(DateUtils.convertDateToString("EEE MMM dd HH:mm:ss zzz yyyy", "dd/MM/yyyy HH:mm:ss", order.getCreatedAt()));
		dto.setAmountDiscount(order.getAmountDiscount() != null ? order.getAmountDiscount() : new BigDecimal(0));
		//dto.setTotalAmount(null);
		if (order.getListOrderDetail() != null) {
			BigDecimal totalAmtDiscount = new BigDecimal(0);
			for (OrderDetail d : order.getListOrderDetail()) {
				totalAmtDiscount = totalAmtDiscount.add(d.getPrice());
			}
			dto.setTotalAmountDiscount(totalAmtDiscount.subtract(dto.getAmountDiscount()));
		}
		//dto.setTotalAmountDiscount(null);
		//dto.setTotalProduct(null);
		if (order.getListImageQR() != null && order.getListImageQR().get(0) != null) {
			FileStorage imageQRCode = order.getListImageQR().get(0);
			dto.setQrCode(imageQRCode.getDirectoryPath() + "/" + imageQRCode.getStorageName());
		}
		dto.setVoucherUsedCode(order.getVoucherUsedCode());
		dto.setPaymentStatus((order.getPaymentStatus() == null || !order.getPaymentStatus()) ? false : true);
		dto.setPaymentTime(order.getPaymentTime());
		//dto.setPaymentTimeStr(DateUtils.convertDateToString("EEE MMM dd HH:mm:ss zzz yyyy", "dd/MM/yyyy HH:mm:ss", order.getPaymentTime()));
		dto.setPaymentAmount(order.getPaymentAmount());
		dto.setPaymentNote(order.getPaymentNote());
		dto.setCartId(null);
		dto.setNote(order.getNote());
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