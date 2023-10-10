package com.flowiee.app.hethong.model.action;

public enum SanPhamAction {
    READ_SANPHAM("Xem danh sách sản phẩm"),
    CREATE_SANPHAM("Thêm mới sản phẩm"),
    UPDATE_SANPHAM("Cập nhật sản phẩm"),
    DELETE_SANPHAM("Xóa sản phẩm"),
    IMPORT_SANPHAM("Import sản phẩm"),
    EXPORT_SANPHAM("Export sản phẩm"),
    UPDATE_PRICE_SANPHAM("Quản lý giá bán"),
    REPORT_SANPHAM("Báo cáo thống kê sản phẩm");

    SanPhamAction(String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}