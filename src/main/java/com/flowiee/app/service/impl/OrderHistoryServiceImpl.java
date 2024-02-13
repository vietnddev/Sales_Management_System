package com.flowiee.app.service.impl;

import com.flowiee.app.entity.OrderHistory;
import com.flowiee.app.repository.OrderHistoryRepository;
import com.flowiee.app.service.OrderHistoryService;
import com.flowiee.app.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {
    @Autowired
    private OrderHistoryRepository orderHistoryRepo;

    @Override
    public List<OrderHistory> findAll() {
        return orderHistoryRepo.findAll();
    }

    @Override
    public OrderHistory findById(Integer orderHistoryId) {
        return orderHistoryRepo.findById(orderHistoryId).orElse(null);
    }

    @Override
    public OrderHistory save(OrderHistory orderHistory) {
        return orderHistoryRepo.save(orderHistory);
    }

    @Override
    public OrderHistory update(OrderHistory orderHistory, Integer orderHistoryId) {
        orderHistory.setId(orderHistoryId);
        return orderHistoryRepo.save(orderHistory);
    }

    @Override
    public String delete(Integer orderHistoryId) {
        orderHistoryRepo.deleteById(orderHistoryId);
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public List<OrderHistory> findByOrderId(Integer orderId) {
        return orderHistoryRepo.findByOrderId(orderId);
    }

    @Override
    public List<OrderHistory> findByOrderDetailId(Integer orderDetailId) {
        return orderHistoryRepo.findByOrderDetailId(orderDetailId);
    }
}