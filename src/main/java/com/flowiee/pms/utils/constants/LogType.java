package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum LogType {
    LI("Đăng nhập"),
    LO("Đăng xuất"),
    R("Xem dữ liệu"),
    I("Thêm mới"),
    U("Cập nhật"),
    D("Xóa"),
    IM("Import data"),
    EX("Export data");

    private final String description;

    LogType(final String description) {
        this.description = description;
    }

    public static LogType fromDescription(String description) {
        for (LogType logType : LogType.values()) {
            if (logType.description.equalsIgnoreCase(description)) {
                return logType;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + description);
    }
}