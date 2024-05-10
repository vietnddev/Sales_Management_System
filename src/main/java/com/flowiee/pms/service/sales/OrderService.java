package com.flowiee.pms.service.sales;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.entity.sales.OrderDetail;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderService extends BaseService<OrderDTO> {
    Page<OrderDTO> findAll(int pageSize, int pageNum, Integer pOrderId, Integer pPaymentMethodId, Integer pOrderStatusId, Integer pSalesChannelId, Integer pSellerId, Integer pCustomerId, LocalDateTime pOrderTimeFrom, LocalDateTime pOrderTimeTo, String pSortBy);

    Optional<OrderDTO> findById(Integer orderId);

    String doPay(Integer orderId, LocalDateTime paymentTime, Integer paymentMethod, Float paymentAmount, String paymentNote);

    List<Order> findOrdersToday();
}