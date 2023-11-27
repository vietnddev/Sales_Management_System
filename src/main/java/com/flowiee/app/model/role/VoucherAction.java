package com.flowiee.app.model.role;

public enum VoucherAction {
    READ_VOUCHER("Xem danh sách voucher"),
    CREATE_VOUCHER("Thêm mới voucher"),
    UPDATE_VOUCHER("Cập nhật voucher"),
    DELETE_VOUCHER("Xóa voucher");

    VoucherAction(String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}