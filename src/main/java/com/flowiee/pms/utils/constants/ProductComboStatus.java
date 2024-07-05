package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum ProductComboStatus {
    A("Đang áp dụng"),
    I("Ngừng áp dụng");
    private final String label;

    ProductComboStatus(String label) {
        this.label = label;
    }
}