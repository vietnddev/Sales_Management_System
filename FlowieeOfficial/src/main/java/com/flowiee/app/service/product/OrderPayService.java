package com.flowiee.app.service.product;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.OrderPay;

import java.util.List;

public interface OrderPayService extends BaseService<OrderPay> {
    List<OrderPay> findByPayMethod(Integer payMethodId);
}