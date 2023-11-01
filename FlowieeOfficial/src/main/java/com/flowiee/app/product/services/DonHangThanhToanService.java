package com.flowiee.app.product.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.product.entity.OrderPay;

import java.util.List;

public interface DonHangThanhToanService extends BaseService<OrderPay> {
    List<OrderPay> findByDonHangId(int id);
}