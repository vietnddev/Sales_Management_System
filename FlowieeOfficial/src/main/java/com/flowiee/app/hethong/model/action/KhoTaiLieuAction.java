package com.flowiee.app.hethong.model.action;

public enum KhoTaiLieuAction {
    READ_DOCUMENT("Xem danh sách tài liệu"),

    CREATE_FOLDER("Thêm mới thư mục"),
    UPDATE_FOLDER("Cập nhật thư mục"),
    DELETE_FOLDER("Xóa thư mục"),
    MOVE_FOLDER("Di chuyển thư mục"),
    COPY_FOLDER("Copy thư mục"),

    CREATE_FILE("Thêm mới tài liệu"),
    UPDATE_DOCUMENT("Cập nhật tài liệu"),
    DELETE_DOCUMENT("Xóa tài liệu"),
    IMPORT_DOCUMENT("Import tài liệu"),
    MOVE_DOCUMENT("Di chuyển tài liệu"),
    COPY_DOCUMENT("Copy tài liệu"),
    DOWNLOAD_DOCUMENT("Download tài liệu"),
    SHARE_DOCUMENT("Chia sẽ tài liệu"),

    READ_DOCTYPE("Xem danh mục loại tài liệu"),
    CREATE_DOCTYPE("Thêm mới loại tài liệu"),
    UPDATE_DOCTYPE("Cập nhật loại tài liệu"),
    DELETE_DOCTYPE("Xóa loại tài liệu"),

    CREATE_DOCFIELD("Thêm mới trường dữ liệu"),
    UPDATE_DOCFIELD("Cập nhật trường dữ liệu"),
    DELETE_DOCFIELD("Xóa trường dữ liệu");

    KhoTaiLieuAction(String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}