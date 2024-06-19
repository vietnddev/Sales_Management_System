package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.repository.sales.OrderRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.OrderStatisticsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderStatisticsServiceImpl extends BaseService implements OrderStatisticsService {
    OrderRepository orderRepository;

    @Override
    public Double findRevenueToday() {
        return orderRepository.findRevenueToday();
    }

    @Override
    public Double findRevenueThisMonth() {
        return 0D;//orderRepository.findRevenueThisMonth();
    }
}