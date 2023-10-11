package com.flowiee.app.common.action;

public enum ThuVienAnhAction {
    READ_ALBUM("Xem thư viện ảnh");

    ThuVienAnhAction(String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}