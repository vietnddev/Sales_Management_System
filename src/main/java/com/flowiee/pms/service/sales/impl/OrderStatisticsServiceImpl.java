package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.repository.sales.OrderRepository;
import com.flowiee.pms.service.sales.OrderStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStatisticsServiceImpl implements OrderStatisticsService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Double findRevenueToday() {
        return orderRepository.findRevenueToday();
    }

    @Override
    public Double findRevenueThisMonth() {
        return 0D;//orderRepository.findRevenueThisMonth();
    }
}