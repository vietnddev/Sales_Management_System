package com.flowiee.pms.service.sales;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.entity.sales.OrderDetail;

import java.util.List;

public interface OrderItemsService extends BaseService<OrderDetail> {
    List<OrderDetail> findByOrderId(Integer pOrderId);
}