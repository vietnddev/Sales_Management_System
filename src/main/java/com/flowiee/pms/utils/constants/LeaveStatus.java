package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum LeaveStatus {
    PENDING("P", "Đang chờ duyệt"),
    APPROVED("A", "Đã duyệt"),
    REJECTED("R", "Từ chối");

    private final String code;
    private String description;

    LeaveStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
}