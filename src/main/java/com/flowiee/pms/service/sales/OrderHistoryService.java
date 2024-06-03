package com.flowiee.pms.service.sales;

import com.flowiee.pms.entity.sales.OrderHistory;

import java.util.List;
import java.util.Map;

public interface OrderHistoryService {
    List<OrderHistory> save(Map<String, Object[]> logChanges, String title, Integer orderId, Integer orderItemId);
}