package com.flowiee.app.product.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.product.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService extends BaseService<OrderDetail> {
    List<OrderDetail> findByDonHangId(Integer donHangId);
}