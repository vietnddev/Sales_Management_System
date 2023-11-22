package com.flowiee.app.model.system;

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
    DOCTYPE_CONFIG_DOCUMENT("Cấu hình loại tài liệu"),
    MANAGEMENT_MATERIAL("Quản lý nguyên vật liệu"),
    MANAGEMENT_GOODS_DRAFT("Tạo phiếu nhập kho hàng hóa"),
    MANAGEMENT_GOODS_APPROVE("Duyệt nhập kho hàng hóa");


    KhoTaiLieuAction(String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}