package com.flowiee.pms.model;

public enum ACTION {
    CTG_R("Xem danh mục hệ thống", "CATEGORY"),
    CTG_C("Thêm mới danh mục", "CATEGORY"),
    CTG_U("Cập nhật danh mục", "CATEGORY"),
    CTG_D("Xóa danh mục", "CATEGORY"),
    CTG_I("Import danh mục", "CATEGORY"),
    CTG_E("Export danh mục", "CATEGORY"),

    PRO_PRD_R("Xem danh sách sản phẩm", "PRODUCT"),
    PRO_PRD_C("Thêm mới sản phẩm", "PRODUCT"),
    PRO_PRD_U("Cập nhật sản phẩm", "PRODUCT"),
    PRO_PRD_D("Xóa sản phẩm", "PRODUCT"),
    PRO_PRD_I("Import sản phẩm", "PRODUCT"),
    PRO_PRD_E("Export sản phẩm", "PRODUCT"),
    PRO_PRD_PRICE("Quản lý giá bán", "PRODUCT"),
    PRO_PRD_RPT("Báo cáo thống kê sản phẩm", "PRODUCT"),
    PRO_ORD_R("Xem danh sách", "PRODUCT"),
    PRO_ORD_C("Thêm mới", "PRODUCT"),
    PRO_ORD_U("Cập nhật", "PRODUCT"),
    PRO_ORD_D("Xóa", "PRODUCT"),
    PRO_ORD_E("Export đơn hàng", "PRODUCT"),
    PRO_CUS_R("Xem danh sách đơn hàng", "PRODUCT"),
    PRO_CUS_C("Thêm mới đơn hàng", "PRODUCT"),
    PRO_CUS_U("Cập nhật đơn hàng", "PRODUCT"),
    PRO_CUS_D("Xóa đơn hàng", "PRODUCT"),
    PRO_SUP_R("Xem danh sách supplier", "PRODUCT"),
    PRO_SUP_C("Thêm mới supplier", "PRODUCT"),
    PRO_SUP_U("Cập nhật supplier", "PRODUCT"),
    PRO_SUP_D("Xóa supplier", "PRODUCT"),
    PRO_VOU_R("Xem danh sách voucher", "PRODUCT"),
    PRO_VOU_C("Thêm mới voucher", "PRODUCT"),
    PRO_VOU_U("Cập nhật voucher", "PRODUCT"),
    PRO_VOU_D("Xóa voucher", "PRODUCT"),
    PRO_GAL_R("Xem thư viện ảnh", "PRODUCT"),

    STG_DASHBOARD("Xem dashboard STG", "PRODUCT"),
    STG_MAT_R("Xem danh sách nguyên vật liệu", "PRODUCT"),
    STG_MAT_C("Thêm mới nguyên vật liệu", "PRODUCT"),
    STG_MAT_U("Cập nhật nguyên vật liệu", "PRODUCT"),
    STG_MAT_D("Xóa nguyên vật liệu", "PRODUCT"),
    STG_TICKET_IM("Nhập hàng", "PRODUCT"),
    STG_TICKET_EX("Xuất hàng", "PRODUCT"),

    SYS_LOGIN("Đăng nhập", "SYSTEM"),
    SYS_LOGOUT("Đăng xuất", "SYSTEM"),
    SYS_ROLE_R("Xem quyền hệ thống", "SYSTEM"),
    SYS_LOG_R("Xem nhật ký hệ thống", "SYSTEM"),
    SYS_RS_PWD("Đổi mật khẩu", "SYSTEM"),
    SYS_ACC_R("Xem danh sách tài khoản", "SYSTEM"),
    SYS_ACC_C("Thêm mới tài khoản", "SYSTEM"),
    SYS_ACC_U("Cập nhật tài khoản", "SYSTEM"),
    SYS_ACC_D("Xóa tài khoản", "SYSTEM"),
    SYS_ACC_RS_PWD("Reset mật khẩu tài khoản", "SYSTEM"),
    SYS_ACC_SHARE_ROLE("Phân quyền tài khoản", "SYSTEM"),
    SYS_GR_ACC_R("Xem danh sách nhóm người dùng", "SYSTEM"),
    SYS_GR_ACC_C("Thêm mới nhóm người dùng", "SYSTEM"),
    SYS_GR_ACC_U("Cập nhật nhóm người dùng", "SYSTEM"),
    SYS_GR_ACC_D("Xóa tài nhóm người dùng", "SYSTEM");

    private final String label;
    private final String module;

    ACTION(String label, String module) {
        this.label = label;
        this.module = module;
    }

    public String getActionKey() {
        return this.name();
    }

    public String getActionLabel() {
        return label;
    }

    public String getModuleKey() {
        return module;
    }

    public String getModuleLabel() {
        if (MODULE.CATEGORY.name().equals(module)) return MODULE.CATEGORY.getLabel();
        if (MODULE.PRODUCT.name().equals(module)) return MODULE.PRODUCT.getLabel();
        if (MODULE.SYSTEM.name().equals(module)) return MODULE.SYSTEM.getLabel();
        return null;
    }
}