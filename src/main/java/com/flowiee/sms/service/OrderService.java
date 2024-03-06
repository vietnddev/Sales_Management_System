package com.flowiee.sms.service;

import com.flowiee.sms.model.dto.OrderDTO;
import com.flowiee.sms.entity.Order;
import com.flowiee.sms.entity.OrderDetail;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public interface OrderService {
    List<OrderDTO> findAllOrder();

    Page<OrderDTO> findAllOrder(int pageSize, int pageNum);

    List<OrderDTO> findAllOrder(Integer orderId);

    List<OrderDetail> findOrderDetailsByOrderId(Integer donHangId);

    OrderDTO findOrderById(Integer orderId);

    OrderDTO saveOrder(OrderDTO orderRequest);

    OrderDetail saveOrderDetail(OrderDetail orderDetail);

    String updateOrder(Order order, Integer orderId);

    String updateOrderDetail(OrderDetail orderDetail, Integer orderDetailId);

    String deleteOrder(Integer orderId);

    String deleteOrderDetail(Integer orderDetailId);

    String doPay(Integer orderId, Date paymentTime, Integer paymentMethod, Float paymentAmount, String paymentNote);

    List<Order> findByStaffId(Integer accountId);

    List<Order> findOrdersBySalesChannelId(Integer salesChannelId);

    List<Order> findOrdersByStatus(Integer orderStatusId);

    List<OrderDTO> findOrdersByCustomerId(Integer customerId);

    List<Order> findOrdersByPaymentMethodId(Integer paymentMethodId);

    List<Order> findOrdersToday();

    OrderDetail findOrderDetailById(Integer orderDetailId);

    Double findRevenueToday();

    Double findRevenueThisMonth();

    ResponseEntity<?> exportDanhSachDonHang();

    void exportToPDF(OrderDTO dto, HttpServletResponse response);
}