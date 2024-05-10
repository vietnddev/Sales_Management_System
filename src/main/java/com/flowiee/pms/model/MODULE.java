package com.flowiee.pms.model;

import lombok.Getter;

@Getter
public enum MODULE {
    PRODUCT("Quản lý sản phẩm"),
    SYSTEM("Quản trị hệ thống"),
    CATEGORY("Danh mục hệ thống"),
    STORAGE("Kho");

    private final String label;

    MODULE(String label) {
        this.label = label;
    }
}