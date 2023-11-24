package com.flowiee.app.model.product;

import com.flowiee.app.category.Category;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.Customer;
import com.flowiee.app.entity.Order;
import com.flowiee.app.entity.OrderPay;
import lombok.Data;

import java.util.Date;

@Data
public class OrderResponse {
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

    public static OrderResponse fromOrder(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(orderResponse.orderId);

        return orderResponse;
    }
}