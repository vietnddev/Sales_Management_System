package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum LedgerReceiptStatus {
    COMPLETED("Hoàn thành"),
    CANCEL("Đã hủy");
    private final String description;

    LedgerReceiptStatus(final String description) {
        this.description = description;
    }
}