package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum PID {
    CLOTHES("CL", "Thời trang"),
    FRUIT("FR", "Hoa quả"),
    SOUVENIR("SO", "Đồ lưu niệm");

    private final String id;
    private final String label;

    PID(String id, String label) {
        this.id = id;
        this.label = label;
    }
}