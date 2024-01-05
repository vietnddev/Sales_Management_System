package com.flowiee.app.dto;

import com.flowiee.app.entity.Category;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.Customer;
import com.flowiee.app.entity.Order;
import com.flowiee.app.entity.OrderPay;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer orderId;
    private String orderCode;
    private Date orderTime;
    private String receiverAddress;
    private String receiverPhone;
	private String receiverEmail;
    private String receiverName;
    private Customer orderBy; //future remove
    private Integer customerId;
    private String customerName;
    private Double totalAmount;
    private Double amountDiscount;
    private Double totalAmountAfterDiscount;
    private Category salesChannel; //future remove
    private Integer salesChannelId;
    private String salesChannelName;
    private String note;
    private Category orderStatus; //future remove
    private Integer orderStatusId;
    private String orderStatusName;
    private OrderPay orderPay; //future remove
    private Integer orderPayId;
    private String orderPayName;
    private Category payMethod; //future remove
    private Integer payMethodId;
    private String payMethodName;
    private Account cashier; //future remove
    private Integer cashierId;
    private String cashierName;
    private Account createdBy; //future remove
    private Integer createdById;
    private String createdByName;
    private Date createdAt;
    private String qrCode;
    private int totalProduct;

    public static OrderDTO fromOrder(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getId());
        dto.setOrderCode(order.getMaDonHang());
        dto.setOrderTime(order.getThoiGianDatHang());
        dto.setReceiverAddress(order.getReceiverAddress());
        dto.setReceiverPhone(order.getReceiverPhone());
        dto.setReceiverName(order.getReceiverName());
		dto.setReceiverEmail(order.getReceiverEmail());
		dto.setReceiverAddress(order.getReceiverAddress());
        //dto.setOrderBy(order.getCustomer());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setCustomerName(order.getCustomer().getTenKhachHang());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setAmountDiscount(order.getAmountDiscount());
        dto.setTotalAmountAfterDiscount(order.getTotalAmountAfterDiscount());
        //dto.setSalesChannel(order.getKenhBanHang());
        dto.setSalesChannelId(order.getKenhBanHang().getId());
        dto.setSalesChannelName(order.getKenhBanHang().getName());
        dto.setNote(order.getGhiChu());
        //dto.setOrderStatus(order.getTrangThaiDonHang());
        dto.setOrderStatusId(order.getTrangThaiDonHang().getId());
        dto.setOrderStatusName(order.getTrangThaiDonHang().getName());
        //dto.setOrderBy(null);
        dto.setOrderPayId(order.getCustomer().getId());
        dto.setOrderPayName(order.getCustomer().getTenKhachHang());
        //dto.setPayMethod(null);
        dto.setPayMethodId(null);
        dto.setPayMethodName(null);
        //dto.setCashier(order.getNhanVienBanHang());
        dto.setCashierId(order.getNhanVienBanHang().getId());
        dto.setCashierName(order.getNhanVienBanHang().getHoTen());
        dto.setCreatedBy(null);
        dto.setCreatedById(order.getCreatedBy());
        dto.setCreatedByName(null);
        dto.setCreatedAt(order.getCreatedAt());
        return dto;
    }

	public static List<OrderDTO> fromOrders(List<Order> orders) {
		List<OrderDTO> list = new ArrayList<>();
		for (Order order : orders){
			list.add(fromOrder(order));
		}
		return list;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderDTO [orderId=");
		builder.append(orderId);
		builder.append(", orderCode=");
		builder.append(orderCode);
		builder.append(", orderTime=");
		builder.append(orderTime);
		builder.append(", receiverAddress=");
		builder.append(receiverAddress);
		builder.append(", receiverPhone=");
		builder.append(receiverPhone);
		builder.append(", receiverName=");
		builder.append(receiverName);
		builder.append(", orderBy=");
		builder.append(orderBy);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", customerName=");
		builder.append(customerName);
		builder.append(", totalAmount=");
		builder.append(totalAmount);
		builder.append(", amountDiscount=");
		builder.append(amountDiscount);
		builder.append(", totalAmountAfterDiscount=");
		builder.append(totalAmountAfterDiscount);
		builder.append(", salesChannel=");
		builder.append(salesChannel);
		builder.append(", salesChannelId=");
		builder.append(salesChannelId);
		builder.append(", salesChannelName=");
		builder.append(salesChannelName);
		builder.append(", note=");
		builder.append(note);
		builder.append(", orderStatus=");
		builder.append(orderStatus);
		builder.append(", orderStatusId=");
		builder.append(orderStatusId);
		builder.append(", orderStatusName=");
		builder.append(orderStatusName);
		builder.append(", orderPay=");
		builder.append(orderPay);
		builder.append(", orderPayId=");
		builder.append(orderPayId);
		builder.append(", orderPayName=");
		builder.append(orderPayName);
		builder.append(", payMethod=");
		builder.append(payMethod);
		builder.append(", payMethodId=");
		builder.append(payMethodId);
		builder.append(", payMethodName=");
		builder.append(payMethodName);
		builder.append(", cashier=");
		builder.append(cashier);
		builder.append(", cashierId=");
		builder.append(cashierId);
		builder.append(", cashierName=");
		builder.append(cashierName);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", createdByName=");
		builder.append(createdByName);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append("]");
		return builder.toString();
	}
}