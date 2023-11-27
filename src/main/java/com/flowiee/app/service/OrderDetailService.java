package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService extends BaseService<OrderDetail> {
    List<OrderDetail> findByDonHangId(Integer donHangId);
}