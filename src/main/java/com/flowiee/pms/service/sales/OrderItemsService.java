package com.flowiee.pms.service.sales;

import com.flowiee.pms.service.BaseCurdService;
import com.flowiee.pms.entity.sales.OrderDetail;

import java.util.List;

public interface OrderItemsService extends BaseCurdService<OrderDetail> {
    List<OrderDetail> findByOrderId(Integer pOrderId);
}