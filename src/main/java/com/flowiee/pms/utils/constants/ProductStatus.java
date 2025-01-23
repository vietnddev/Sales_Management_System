package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum ProductStatus {
    ACT("Đang kinh doanh"),
    INA("Ngừng kinh doanh"),
    DIS("Ngừng kinh doanh"),
    OOS("Hết hàng");

    private String label;

    ProductStatus(String label) {
        this.label = label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}