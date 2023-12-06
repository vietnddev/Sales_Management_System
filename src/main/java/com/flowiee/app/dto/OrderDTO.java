package com.flowiee.app.dto;

import com.flowiee.app.entity.Category;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.Customer;
import com.flowiee.app.entity.Order;
import com.flowiee.app.entity.OrderPay;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer orderId;
    private String orderCode;
    private Date orderTime;
    private String receiverAddress;
    private String receiverPhone;
    private String receiverName;
    private Customer orderBy; //future remove
    private Integer customerId;
    private String customerName;
    private Double totalAmount;
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

    public static OrderDTO fromOrder(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getId());
        dto.setOrderCode(order.getMaDonHang());
        dto.setOrderTime(order.getThoiGianDatHang());
        dto.setReceiverAddress(order.getReceiverAddress());
        dto.setReceiverPhone(order.getReceiverPhone());
        dto.setReceiverName(order.getReceiverName());
        //dto.setOrderBy(order.getCustomer());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setCustomerName(order.getCustomer().getTenKhachHang());
        dto.setTotalAmount(order.getTongTienDonHang());
        //dto.setSalesChannel(order.getKenhBanHang());
        dto.setSalesChannelId(order.getKenhBanHang().getId());
        dto.setSalesChannelName(order.getKenhBanHang().getName());
        dto.setNote(order.getGhiChu());
        //dto.setOrderStatus(order.getTrangThaiDonHang());
        dto.setOrderStatusId(order.getTrangThaiDonHang().getId());
        dto.setOrderStatusName(order.getTrangThaiDonHang().getName());
        //dto.setOrderBy(null);
        dto.setOrderPayId(null);
        dto.setOrderPayName(null);
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
}