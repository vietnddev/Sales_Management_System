package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.entity.sales.OrderDetail;
import com.flowiee.pms.entity.sales.OrderHistory;
import com.flowiee.pms.repository.sales.OrderHistoryRepository;
import com.flowiee.pms.service.sales.OrderHistoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Override
    public List<OrderHistory> save(Map<String, Object[]> logChanges, String title, Integer orderId, Integer orderItemId) {
        List<OrderHistory> orderHistories = new ArrayList<>();
        for (Map.Entry<String, Object[]> entry : logChanges.entrySet()) {
            String field = entry.getKey();
            String oldValue = ObjectUtils.isNotEmpty(entry.getValue()[0]) ? entry.getValue()[0].toString() : " ";
            String newValue = ObjectUtils.isNotEmpty(entry.getValue()[1]) ? entry.getValue()[1].toString() : " ";

            OrderHistory orderHistory = new OrderHistory();
            if (orderId != null) {
                orderHistory.setOrder(new Order(orderId));
            }
            if (orderItemId != null) {
                orderHistory.setOrderDetail(new OrderDetail(orderItemId));
            }
            orderHistory.setTitle(title);
            orderHistory.setField(field);
            orderHistory.setOldValue(oldValue);
            orderHistory.setNewValue(newValue);

            orderHistories.add(orderHistoryRepository.save(orderHistory));
        }
        return orderHistories;
    }
}