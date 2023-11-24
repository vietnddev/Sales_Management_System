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

    private int orderId;
    private String orderCode;
    private Date orderTime;
    private String receiverAddress;
    private String receiverPhone;
    private String receiverName;
    private Customer orderBy;
    private double totalAmount;
    private Category salesChannel;
    private String note;
    private Category orderStatus;
    private OrderPay orderPay;
    private Category payMethod;
    private Account cashier;
    private Account createdBy;
    private Date createdAt;

    public static OrderDTO fromOrder(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderDTO.orderId);

        return orderDTO;
    }
}