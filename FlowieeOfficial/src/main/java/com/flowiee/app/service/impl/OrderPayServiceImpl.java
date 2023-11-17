package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.OrderPay;
import com.flowiee.app.repository.product.OrderPayRepository;
import com.flowiee.app.service.product.OrderPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPayServiceImpl implements OrderPayService {
    @Autowired
    private OrderPayRepository orderPayRepository;

    @Override
    public List<OrderPay> findAll() {
        return orderPayRepository.findAll();
    }

    @Override
    public OrderPay findById(Integer entityId) {
        return orderPayRepository.findById(entityId).get();
    }

    @Override
    public String save(OrderPay entity) {
        orderPayRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(OrderPay entity, Integer entityId) {
        entity.setId(entityId);
        orderPayRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        orderPayRepository.deleteById(entityId);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public List<OrderPay> findByPayMethod(Integer payMethodId) {
        return orderPayRepository.findByPayMethod(payMethodId);
    }
}