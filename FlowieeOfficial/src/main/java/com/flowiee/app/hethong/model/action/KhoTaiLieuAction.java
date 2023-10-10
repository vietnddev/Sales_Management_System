package com.flowiee.app.hethong.model.action;

public enum KhoTaiLieuAction {
    DASHBOARD_DOCUMENT("Xem dashboard STG"),
    READ_DOCUMENT("Xem danh sách tài liệu"),
    CREATE_DOCUMENT("Thêm mới tài liệu"),
    UPDATE_DOCUMENT("Cập nhật tài liệu"),
    DELETE_DOCUMENT("Xóa tài liệu"),
    MOVE_DOCUMENT("Di chuyển tài liệu"),
    COPY_DOCUMENT("Copy tài liệu"),
    DOWNLOAD_DOCUMENT("Download tài liệu"),
    SHARE_DOCUMENT("Chia sẽ tài liệu"),
    DOCTYPE_CONFIG_DOCUMENT("Cấu hình loại tài liệu");


    KhoTaiLieuAction(String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}