package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum TicketExportAction {
    INIT("Khởi tạo variant - EX"),
    RELEASE_TO_CUSTOMER("Xuất kho giao hàng cho khách/shipper");

    private final String label;

    TicketExportAction(String label) {
        this.label = label;
    }
}