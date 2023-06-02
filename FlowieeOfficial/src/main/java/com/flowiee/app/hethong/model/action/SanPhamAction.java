package com.flowiee.app.hethong.model.action;

public enum SanPhamAction {
    READ_SANPHAM("Xem danh sách sản phẩm"),
    CREATE_SANPHAM("Thêm mới sản phẩm"),
    UPDATE_SANPHAM("Cập nhật sản phẩm"),
    DELETE_SANPHAM("Xóa sản phẩm"),
    IMPORT_SANPHAM("Import sản phẩm"),
    UPLOAD_IMAGE_SANPHAM("Upload hình sản phẩm"),
    PRICE_SANPHAM("Quản lý giá bán"),
    REPORT_SANPHAM("Báo cáo thống kê sản phẩm"),

    CREATE_SANPHAM_BIENTHE("Thêm mới biến thể sản phẩm"),
    UPDATE_SANPHAM_BIENTHE("Cập nhật biến thể sản phẩm"),
    DELETE_SANPHAM_BIENTHE("Xóa biến thể sản phẩm"),

    CREATE_SANPHAM_THUOCTINH("Thêm mới thuộc tính sản phẩm"),
    UPDATE_SANPHAM_THUOCTINH("Cập nhật thuộc tính sản phẩm"),
    DELETE_SANPHAM_THUOCTINH("Xóa thuộc tính sản phẩm"),

    CREATE_SANPHAM_GIABAN("Thêm mới giá sản phẩm"),
    UPDATE_SANPHAM_GIABAN("Cập nhật giá sản phẩm"),
    DELETE_SANPHAM_GIABAN("Xóa giá sản phẩm");

    SanPhamAction(String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}