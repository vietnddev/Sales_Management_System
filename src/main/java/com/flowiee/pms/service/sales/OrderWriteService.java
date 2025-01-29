package com.flowiee.pms.service.sales;

import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.model.payload.CreateOrderReq;
import com.flowiee.pms.model.payload.UpdateOrderReq;

public interface OrderWriteService {
    Order createOrder(CreateOrderReq pRequest);

    Order updateOrder(UpdateOrderReq pRequest, Long pOrderId);

    String deleteOrder(Long pOrderId);
}