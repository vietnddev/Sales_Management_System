package com.flowiee.app.service;

import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.entity.Order;
import com.flowiee.app.entity.OrderDetail;
import com.flowiee.app.model.request.OrderRequest;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    List<OrderDTO> findAllOrder();

    List<OrderDTO> findAllOrder(Integer orderId);

    List<Order> findByStaffId(Integer accountId);

    List<Order> findOrdersBySalesChannelId(Integer salesChannelId);

    List<Order> findOrdersByStatus(Integer orderStatusId);

    List<Order> findOrdersByCustomerId(Integer customerId);

    List<Order> findOrdersToday();

    OrderDTO findOrderById(Integer orderId);

    OrderDetail findOrderDetailById(Integer orderDetailId);

    String saveOrder(Order order);

    String saveOrder(OrderRequest orderRequest);

    String saveOrderDetail(OrderDetail orderDetail);

    String updateOrder(Order order, Integer orderId);

    String updateOrderDetail(OrderDetail orderDetail, Integer orderDetailId);

    String deleteOrder(Integer orderId);

    String deleteOrderDetail(Integer orderDetailId);

    ResponseEntity<?> exportDanhSachDonHang();

    Double findRevenueToday();

    Double findRevenueThisMonth();

    List<OrderDetail> findOrderDetailsByOrderId(Integer donHangId);
}