package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum TICKET_EXPORT_ACTION {
    INIT("Khởi tạo variant - EX"),
    RELEASE_TO_CUSTOMER("Xuất kho giao hàng cho khách/shipper");

    private final String label;

    TICKET_EXPORT_ACTION(String label) {
        this.label = label;
    }
}