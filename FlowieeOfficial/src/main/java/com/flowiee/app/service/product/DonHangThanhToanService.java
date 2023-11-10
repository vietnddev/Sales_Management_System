package com.flowiee.app.service.product;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.product.OrderPay;

import java.util.List;

public interface DonHangThanhToanService extends BaseService<OrderPay> {
    List<OrderPay> findByDonHangId(int id);
}