package com.flowiee.app.model.system;

public enum DanhMucAction {
    READ_DANHMUC("Xem danh mục hệ thống"),
    CREATE_DANHMUC("Thêm mới danh mục"),
    UPDATE_DANHMUC("Cập nhật danh mục"),
    DELETE_DANHMUC("Xóa danh mục"),
    IMPORT_DANHMUC("Import danh mục"),
    EXPORT_DANHMUC("Export danh mục");

    DanhMucAction(String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}
