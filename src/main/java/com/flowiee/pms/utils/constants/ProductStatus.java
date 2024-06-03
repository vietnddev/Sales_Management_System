package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum ProductStatus {
    A("Đang kinh doanh"),
    I("Ngừng kinh doanh");
    private final String label;

    ProductStatus(String label) {
        this.label = label;
    }
}