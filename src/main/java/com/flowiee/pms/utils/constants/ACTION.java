package com.flowiee.pms.utils.constants;

public enum ACTION {
    READ_DASHBOARD("Xem dashboard", "DASHBOARD"),

    CTG_R("View list of system categories", "CATEGORY"),
    CTG_C("Create category", "CATEGORY"),
    CTG_U("Update category", "CATEGORY"),
    CTG_D("Delete category", "CATEGORY"),
    CTG_I("Import category", "CATEGORY"),
    CTG_E("Export category", "CATEGORY"),

    PRO_PRD_R("View list of products", "PRODUCT"),
    PRO_PRD_C("Create product", "PRODUCT"),
    PRO_PRD_U("Update product", "PRODUCT"),
    PRO_PRD_D("Delete product", "PRODUCT"),
    PRO_PRD_I("Import product", "PRODUCT"),
    PRO_PRD_E("Export product", "PRODUCT"),
    PRO_PRD_PRICE("Product's price", "PRODUCT"),
    PRO_PRD_RPT("View product's report", "PRODUCT"),
    PRO_CBO_R("View list of combos", "PRODUCT"),
    PRO_CBO_C("Create combo", "PRODUCT"),
    PRO_CBO_U("Update combo", "PRODUCT"),
    PRO_CBO_D("Delete combo", "PRODUCT"),
    PRO_CART_C("Tạo giỏ hàng", MODULE.SALES.name()),
    PRO_ORD_R("View list of orders", "PRODUCT"),
    PRO_ORD_C("Create order", "PRODUCT"),
    PRO_ORD_U("Update order", "PRODUCT"),
    PRO_ORD_D("Delete order", "PRODUCT"),
    PRO_ORD_E("Export order", "PRODUCT"),
    PRO_CUS_R("View list of customers", "PRODUCT"),
    PRO_CUS_C("Create customer", "PRODUCT"),
    PRO_CUS_U("Update customer", "PRODUCT"),
    PRO_CUS_D("Delete customer", "PRODUCT"),
    PRO_SUP_R("View list of suppliers", "PRODUCT"),
    PRO_SUP_C("Create supplier", "PRODUCT"),
    PRO_SUP_U("Update supplier", "PRODUCT"),
    PRO_SUP_D("Delete supplier", "PRODUCT"),
    PRO_VOU_R("View list of vouchers", "PRODUCT"),
    PRO_VOU_C("Create voucher", "PRODUCT"),
    PRO_VOU_U("Update voucher", "PRODUCT"),
    PRO_VOU_D("Delete voucher", "PRODUCT"),
    PRO_PROMO_R("View list of promotions", "PRODUCT"),
    PRO_PROMO_C("Create promotion", "PRODUCT"),
    PRO_PROMO_U("Update promotion", "PRODUCT"),
    PRO_PROMO_D("Delete promotion", "PRODUCT"),
    PRO_GAL_R("Gallery", "PRODUCT"),

    SLS_RCT_R("Xem danh sách phiếu thu", "SALES"),
    SLS_RCT_C("Tạo phiếu thu", "SALES"),
    SLS_RCT_U("Cập nhật phiếu thu", "SALES"),
    SLS_PMT_R("Xem danh sách phiếu chi", "SALES"),
    SLS_PMT_C("Tạo phiếu chi", "SALES"),
    SLS_PMT_U("Cập nhật phiếu chi", "SALES"),
    SLS_LEDGER("Xem sổ quỹ", "SALES"),

    STG_DASHBOARD("Xem dashboard STG", "PRODUCT"),
    STG_MAT_R("View list of materials", "PRODUCT"),
    STG_MAT_C("Create material", "PRODUCT"),
    STG_MAT_U("Update material", "PRODUCT"),
    STG_MAT_D("Delete material", "PRODUCT"),
    STG_TICKET_IM("Import goods into storage", "PRODUCT"),
    STG_TICKET_EX("Export goods", "PRODUCT"),

    STG_STORAGE("Storage management", "STORAGE"),
    STG_STG_C("Thêm mới kho", "STORAGE"),
    STG_STG_U("Cập nhật kho", "STORAGE"),
    STG_STG_D("Xóa kho", "STORAGE"),

    SYS_LOGIN("Login", "SYSTEM"),
    SYS_ROLE_R("View list of system rights", "SYSTEM"),
    SYS_LOG_R("View list of system logs", "SYSTEM"),
    SYS_ACC_R("View list of accounts", "SYSTEM"),
    SYS_ACC_C("Create account", "SYSTEM"),
    SYS_ACC_U("Update account", "SYSTEM"),
    SYS_ACC_D("Delete account", "SYSTEM"),
    SYS_ACC_RS_PWD("Reset password", "SYSTEM"),
    SYS_ACC_SHARE_ROLE("Grant permissions", "SYSTEM"),
    SYS_GR_ACC_R("View list of account groups", "SYSTEM"),
    SYS_GR_ACC_C("Create account group", "SYSTEM"),
    SYS_GR_ACC_U("Update account group", "SYSTEM"),
    SYS_GR_ACC_D("Delete account group", "SYSTEM"),
    SYS_CNF_R("View system config", "SYSTEM"),
    SYS_CNF_U("Update system config", "SYSTEM");

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