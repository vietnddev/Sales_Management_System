package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum LedgerPaymentStatus {
    COMPLETED("Hoàn thành"),
    CANCEL("Đã hủy");
    private final String description;

    LedgerPaymentStatus(final String description) {
        this.description = description;
    }
}