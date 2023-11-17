package com.flowiee.app.service.impl;

import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.OrderPay;
import com.flowiee.app.repository.product.OrderPayRepository;
import com.flowiee.app.service.product.DonHangThanhToanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonHangThanhToanServiceImpl implements DonHangThanhToanService {
    @Autowired
    private OrderPayRepository orderPayRepository;

    @Override
    public List<OrderPay> findAll() {
        return orderPayRepository.findAll();
    }

    @Override
    public List<OrderPay> findByDonHangId(int id) {
        return orderPayRepository.findByDonHangId(id);
    }

    @Override
    public OrderPay findById(Integer id) {
        return orderPayRepository.findById(id).orElse(null);
    }

    @Override
    public String save(OrderPay orderPay) {
        if (orderPay.getOrder().getId() <= 0) {
            throw new BadRequestException();
        }
        try {
            orderPayRepository.save(orderPay);
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getCause().getMessage();
        }
    }

    @Override
    public String update(OrderPay orderPay, Integer id) {
        try {
            orderPay.setId(id);
            orderPayRepository.save(orderPay);
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getCause().getMessage();
        }
    }

    @Override
    public String delete(Integer id) {
        try {
            orderPayRepository.deleteById(id);
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getCause().getMessage();
        }
    }
}