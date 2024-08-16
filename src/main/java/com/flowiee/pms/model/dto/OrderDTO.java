package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.entity.sales.OrderDetail;
import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.utils.CommonUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO extends Order implements Serializable {
	@Serial
	static final long serialVersionUID = 1L;

    Integer customerId;
    String customerName;
    Integer salesChannelId;
    String salesChannelName;
    Integer orderStatusId;
    String orderStatusName;
    Integer payMethodId;
    String payMethodName;
    Integer cashierId;
    String cashierName;
	BigDecimal totalAmount;
	BigDecimal totalAmountDiscount;
	Integer totalProduct;
    String qrCode;
	Integer cartId;
	Integer ticketExportId;
	Boolean accumulateBonusPoints;
	Boolean autoGenLedgerReceipt;
	List<OrderDetailDTO> listOrderDetailDTO;

	public static OrderDTO fromOrder(Order order) {
		OrderDTO dto = new OrderDTO();
		dto.setId(order.getId());
		dto.setCode(order.getCode());
		dto.setOrderTime(order.getOrderTime());
		//dto.setOrderTimeStr(DateUtils.convertDateToString("EEE MMM dd HH:mm:ss zzz yyyy", "dd/MM/yyyy HH:mm:ss", order.getThoiGianDatHang()));
		dto.setReceiverAddress(CommonUtils.trim(order.getReceiverAddress()));
		dto.setReceiverPhone(CommonUtils.trim(order.getReceiverPhone()));
		dto.setReceiverEmail(CommonUtils.trim(order.getReceiverEmail()));
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
		dto.setCreatedAt(order.getCreatedAt());
		//dto.setCreatedAtStr(DateUtils.convertDateToString("EEE MMM dd HH:mm:ss zzz yyyy", "dd/MM/yyyy HH:mm:ss", order.getCreatedAt()));
		dto.setAmountDiscount(order.getAmountDiscount() != null ? order.getAmountDiscount() : new BigDecimal(0));
		//dto.setTotalAmount(null);
		BigDecimal totalAmount = BigDecimal.ZERO;
		if (order.getListOrderDetail() != null) {
			for (OrderDetail d : order.getListOrderDetail()) {
				totalAmount = totalAmount.add((d.getPrice().multiply(BigDecimal.valueOf(d.getQuantity()))).subtract(d.getExtraDiscount()));
			}
		}
		dto.setTotalAmount(totalAmount);
		dto.setTotalAmountDiscount(dto.getTotalAmount().subtract(dto.getAmountDiscount()));
		//dto.setTotalAmountDiscount(null);
		//dto.setTotalProduct(null);
		if (ObjectUtils.isNotEmpty(order.getListImageQR())) {
			FileStorage imageQRCode = order.getListImageQR().get(0);
			dto.setQrCode(imageQRCode.getDirectoryPath() + "/" + imageQRCode.getStorageName());
		}
		dto.setVoucherUsedCode(order.getVoucherUsedCode());
		dto.setPaymentStatus(order.getPaymentStatus() != null && order.getPaymentStatus());
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
		dto.setTrangThaiDonHang(order.getTrangThaiDonHang());
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