package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum ProductStatus {
    A("Đang kinh doanh"),
    I("Ngừng kinh doanh"),
    OOS("Hết hàng");//Out of stock
    private final String label;

    ProductStatus(String label) {
        this.label = label;
    }
}