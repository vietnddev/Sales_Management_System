package com.flowiee.pms.service.sales;

import com.flowiee.pms.service.CrudService;
import com.flowiee.pms.entity.sales.OrderDetail;

import java.util.List;

public interface OrderItemsService extends CrudService<OrderDetail> {
    List<OrderDetail> findByOrderId(Integer pOrderId);
}