package com.flowiee.app.service;

import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.entity.Order;
import com.flowiee.app.entity.OrderDetail;
import com.flowiee.app.model.request.OrderRequest;

import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface OrderService {
    List<OrderDTO> findAllOrder();

    List<OrderDTO> findAllOrder(Integer orderId);

    List<OrderDetail> findOrderDetailsByOrderId(Integer donHangId);

    OrderDTO findOrderById(Integer orderId);

    String saveOrder(OrderDTO orderRequest);

    String saveOrderDetail(OrderDetail orderDetail);

    String updateOrder(Order order, Integer orderId);

    String updateOrderDetail(OrderDetail orderDetail, Integer orderDetailId);

    String deleteOrder(Integer orderId);

    String deleteOrderDetail(Integer orderDetailId);

    String doPay(Integer orderId, Date paymentTime, Integer paymentMethod, String note);

    List<Order> findByStaffId(Integer accountId);

    List<Order> findOrdersBySalesChannelId(Integer salesChannelId);

    List<Order> findOrdersByStatus(Integer orderStatusId);

    List<Order> findOrdersByCustomerId(Integer customerId);

    List<Order> findOrdersByPaymentMethodId(Integer paymentMethodId);

    List<Order> findOrdersToday();

    OrderDetail findOrderDetailById(Integer orderDetailId);

    Double findRevenueToday();

    Double findRevenueThisMonth();

    ResponseEntity<?> exportDanhSachDonHang();
}