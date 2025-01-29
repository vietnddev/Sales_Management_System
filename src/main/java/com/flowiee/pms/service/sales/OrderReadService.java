package com.flowiee.pms.service.sales;

import com.flowiee.pms.entity.sales.Order;

import com.flowiee.pms.utils.constants.OrderStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderReadService {
    List<Order> findAll();

    Page<Order> findAll(int pageSize, int pageNum, String pTxtSearch, Long pOrderId, Long pPaymentMethodId,
                           OrderStatus pOrderStatus, Long pSalesChannelId, Long pSellerId, Long pCustomerId,
                           Long pBranchId, Long pGroupCustomerId, String pDateFilter, LocalDateTime pOrderTimeFrom, LocalDateTime pOrderTimeTo, String pSortBy);

    Order findById(Long orderId, boolean throwException);

    List<Order> findOrdersToday();

    Page<Order> getOrdersByCustomer(int pageSize, int pageNum, Long pCustomerId);

    String updateOrderStatus(Long pOrderId, OrderStatus pOrderStatus, LocalDateTime pSuccessfulDeliveryTime, Long cancellationReasonId);

    Order getOrderByCode(String pOrderCode);
}