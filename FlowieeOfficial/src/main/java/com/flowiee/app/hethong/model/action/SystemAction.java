package com.flowiee.app.hethong.model.action;

public enum SystemAction {
    LOGIN("Đăng nhập"),
    LOGOUT("Đăng xuất"),
    RESET_PASSWORD("Đổi mật khẩu");

    SystemAction(String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}