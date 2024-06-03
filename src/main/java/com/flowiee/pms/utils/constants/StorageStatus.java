package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum StorageStatus {
    A("Đang được sử dụng"),
    I("Không sử dụng");
    private final String label;

    StorageStatus(String label) {
        this.label = label;
    }
}