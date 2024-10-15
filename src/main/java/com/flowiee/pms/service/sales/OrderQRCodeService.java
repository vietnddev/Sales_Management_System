package com.flowiee.pms.service.sales;

import com.flowiee.pms.entity.system.FileStorage;

public interface OrderQRCodeService {
    String saveQRCodeOfOrder(long orderId);

    FileStorage findQRCodeOfOrder(long orderId);
}