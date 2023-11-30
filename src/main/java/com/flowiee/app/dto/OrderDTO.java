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
    private Integer orderStatusName;
    private OrderPay orderPay; //future remove
    private Integer orderPayId;
    private Integer orderPayName;
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
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(orderDTO.orderId);

        return orderDTO;
    }
}