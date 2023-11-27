package com.flowiee.app.model.role;

public enum AccountAction {
    READ_ACCOUNT("Xem danh sách tài khoản"),
    CREATE_ACCOUNT("Thêm mới tài khoản"),
    UPDATE_ACCOUNT("Cập nhật tài khoản"),
    DELETE_ACCOUNT("Xóa tài khoản"),
    RESET_PASSWORD_ACCOUNT("Reset mật khẩu tài khoản"),
    SHARE_ROLE_ACCOUNT("Phân quyền tài khoản");

    AccountAction(String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}