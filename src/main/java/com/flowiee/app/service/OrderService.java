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

    OrderDTO findOrderById(Integer orderId);

    String saveOrder(Order order);

    String saveOrder(OrderRequest orderRequest);

    String updateOrder(Order order, Integer orderId);

    List<Order> findByStaffId(Integer accountId);

    List<Order> findOrdersBySalesChannelId(Integer salesChannelId);

    List<Order> findOrdersByStatus(Integer orderStatusId);

    List<Order> findOrdersByCustomerId(Integer customerId);

    List<Order> findOrdersToday();

    OrderDetail findOrderDetailById(Integer orderDetailId);

    String saveOrderDetail(OrderDetail orderDetail);

    String updateOrderDetail(OrderDetail orderDetail, Integer orderDetailId);

    String deleteOrder(Integer orderId);

    String deleteOrderDetail(Integer orderDetailId);

    ResponseEntity<?> exportDanhSachDonHang();

    List<Order> findBySalesChannel(Integer salesChannelId);

    List<Order> findByOrderStatus(Integer orderStatusId);

    List<Order> findByCustomer(Integer customerId);

    Double findRevenueToday();

    Double findRevenueThisMonth();

    List<OrderDetail> findOrderDetailsByOrderId(Integer donHangId);
}