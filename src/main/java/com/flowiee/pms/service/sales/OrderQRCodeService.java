package com.flowiee.pms.service.sales;

import com.flowiee.pms.entity.system.FileStorage;

public interface OrderQRCodeService {
    String saveQRCodeOfOrder(int orderId);

    FileStorage findQRCodeOfOrder(int orderId);
}