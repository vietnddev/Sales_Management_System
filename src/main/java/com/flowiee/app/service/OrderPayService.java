package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.OrderPay;

import java.util.List;

public interface OrderPayService extends BaseService<OrderPay> {
    List<OrderPay> findAll();

    List<OrderPay> findByPayMethod(Integer payMethodId);

    List<OrderPay> findByOrder(Integer orderId);
}