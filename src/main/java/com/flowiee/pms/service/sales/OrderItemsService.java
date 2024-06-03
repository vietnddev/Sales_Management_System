package com.flowiee.pms.service.sales;

import com.flowiee.pms.service.BaseCurd;
import com.flowiee.pms.entity.sales.OrderDetail;

import java.util.List;

public interface OrderItemsService extends BaseCurd<OrderDetail> {
    List<OrderDetail> findByOrderId(Integer pOrderId);
}