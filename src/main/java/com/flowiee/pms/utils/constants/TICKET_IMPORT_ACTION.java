package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum TICKET_IMPORT_ACTION {
    INIT("Khởi tạo variant - IM"),
    CANCELED_DELIVERY("Nhận hàng hủy giao"),
    IMPORT_INTO_STORAGE("Nhập hàng vào kho");

    private final String label;

    TICKET_IMPORT_ACTION(String label) {
        this.label = label;
    }
}