package com.flowiee.sms.model;

public enum ACTION {
    CTG_READ("Xem danh mục hệ thống", "CATEGORY"),
    CTG_CREATE("Thêm mới danh mục", "CATEGORY"),
    CTG_UPDATE("Cập nhật danh mục", "CATEGORY"),
    CTG_DELETE("Xóa danh mục", "CATEGORY"),
    CTG_IMPORT("Import danh mục", "CATEGORY"),
    CTG_EXPORT("Export danh mục", "CATEGORY"),

    PRO_PRODUCT_READ("Xem danh sách sản phẩm", "PRODUCT"),
    PRO_PRODUCT_CREATE("Thêm mới sản phẩm", "PRODUCT"),
    PRO_PRODUCT_UPDATE("Cập nhật sản phẩm", "PRODUCT"),
    PRO_PRODUCT_DELETE("Xóa sản phẩm", "PRODUCT"),
    PRO_PRODUCT_IMPORT("Import sản phẩm", "PRODUCT"),
    PRO_PRODUCT_EXPORT("Export sản phẩm", "PRODUCT"),
    PRO_PRODUCT_PRICE("Quản lý giá bán", "PRODUCT"),
    PRO_PRODUCT_REPORT("Báo cáo thống kê sản phẩm", "PRODUCT"),
    PRO_ORDERS_READ("Xem danh sách", "PRODUCT"),
    PRO_ORDERS_CREATE("Thêm mới", "PRODUCT"),
    PRO_ORDERS_UPDATE("Cập nhật", "PRODUCT"),
    PRO_ORDERS_DELETE("Xóa", "PRODUCT"),
    PRO_ORDERS_EXPORT("Export đơn hàng", "PRODUCT"),
    PRO_CUSTOMER_READ("Xem danh sách đơn hàng", "PRODUCT"),
    PRO_CUSTOMER_CREATE("Thêm mới đơn hàng", "PRODUCT"),
    PRO_CUSTOMER_UPDATE("Cập nhật đơn hàng", "PRODUCT"),
    PRO_CUSTOMER_DELETE("Xóa đơn hàng", "PRODUCT"),
    PRO_SUPPLIER_READ("Xem danh sách supplier", "PRODUCT"),
    PRO_SUPPLIER_CREATE("Thêm mới supplier", "PRODUCT"),
    PRO_SUPPLIER_UPDATE("Cập nhật supplier", "PRODUCT"),
    PRO_SUPPLIER_DELETE("Xóa supplier", "PRODUCT"),
    PRO_VOUCHER_READ("Xem danh sách voucher", "PRODUCT"),
    PRO_VOUCHER_CREATE("Thêm mới voucher", "PRODUCT"),
    PRO_VOUCHER_UPDATE("Cập nhật voucher", "PRODUCT"),
    PRO_VOUCHER_DELETE("Xóa voucher", "PRODUCT"),
    PRO_GALLERY_READ("Xem thư viện ảnh", "PRODUCT"),

    STG_DASHBOARD("Xem dashboard STG", "STORAGE"),
    STG_MATERIAL_READ("Xem danh sách nguyên vật liệu", "STORAGE"),
    STG_MATERIAL_CREATE("Thêm mới nguyên vật liệu", "STORAGE"),
    STG_MATERIAL_UPDATE("Cập nhật nguyên vật liệu", "STORAGE"),
    STG_MATERIAL_DELETE("Xóa nguyên vật liệu", "STORAGE"),
    STG_TICKET_IMPORT_GOODS("Nhập hàng", "STORAGE"),
    STG_TICKET_EXPORT_GOODS("Xuất hàng", "STORAGE"),

    SYS_ROLE_READ("Xem quyền hệ thống", "SYSTEM"),
    SYS_LOG_READ("Xem nhật ký hệ thống", "SYSTEM"),
    SYS_LOGIN("Đăng nhập", "SYSTEM"),
    SYS_LOGOUT("Đăng xuất", "SYSTEM"),
    SYS_RESET_PASSWORD("Đổi mật khẩu", "SYSTEM"),
    SYS_ACCOUNT_READ("Xem danh sách tài khoản", "SYSTEM"),
    SYS_ACCOUNT_CREATE("Thêm mới tài khoản", "SYSTEM"),
    SYS_ACCOUNT_UPDATE("Cập nhật tài khoản", "SYSTEM"),
    SYS_ACCOUNT_DELETE("Xóa tài khoản", "SYSTEM"),
    SYS_ACCOUNT_RESET_PASSWORD("Reset mật khẩu tài khoản", "SYSTEM"),
    SYS_ACCOUNT_SHARE_ROLE("Phân quyền tài khoản", "SYSTEM");

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