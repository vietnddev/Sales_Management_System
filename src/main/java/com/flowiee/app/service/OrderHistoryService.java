package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.OrderHistory;

import java.util.List;

public interface OrderHistoryService extends BaseService<OrderHistory> {
    List<OrderHistory> findByOrderId(Integer orderId);

    List<OrderHistory> findByOrderDetailId(Integer orderDetailId);

    List<OrderHistory> findByOrderPayId(Integer orderPayId);
}