package com.flowiee.pms.utils.constants;

public enum Pages {
    CTG_CATEGORY(MODULE.SYSTEM, "category"),
    CTG_CATEGORY_DETAIL(MODULE.SYSTEM, "category-detail"),
    SYS_LOGIN(MODULE.SYSTEM, "login"),
    SYS_ACCOUNT(MODULE.SYSTEM, "account"),
    SYS_ACCOUNT_DETAIL(MODULE.SYSTEM, "account-detail"),
    SYS_GR_ACC(MODULE.SYSTEM, "group-account"),
    SYS_GR_ACC_DETAIL(MODULE.SYSTEM, "group-account-detail"),
    SYS_LOG(MODULE.SYSTEM, "log"),
    SYS_ROLE(MODULE.SYSTEM, "role"),
    SYS_CONFIG(MODULE.SYSTEM, "config"),
    SYS_NOTIFICATION(MODULE.SYSTEM, "notification"),
    SYS_PROFILE(MODULE.SYSTEM, "profile"),
    SYS_UNAUTHORIZED(MODULE.SYSTEM, "unauthorized"),
    SYS_ERROR(MODULE.SYSTEM, "error"),

    PRO_DASHBOARD(MODULE.PRODUCT, "dashboard"),
    PRO_GALLERY(MODULE.PRODUCT, "gallery"),
    PRO_PRODUCT(MODULE.PRODUCT, "product"),
    PRO_PRODUCT_INFO(MODULE.PRODUCT, "product-info"),
    PRO_PRODUCT_VARIANT(MODULE.PRODUCT, "product-variant"),
    PRO_VOUCHER(MODULE.PRODUCT, "vouchers"),
    PRO_VOUCHER_DETAIL(MODULE.PRODUCT, "voucher-detail"),
    PRO_ORDER(MODULE.PRODUCT, "order"),
    PRO_ORDER_DETAIL(MODULE.PRODUCT, "order-detail"),
    PRO_ORDER_SELL(MODULE.PRODUCT, "sell"),
    PRO_CUSTOMER(MODULE.PRODUCT, "customer"),
    PRO_CUSTOMER_DETAIL(MODULE.PRODUCT, "customer-detail"),
    PRO_SUPPLIER(MODULE.PRODUCT, "supplier"),
    PRO_PROMOTION(MODULE.PRODUCT, "promotion"),
    PRO_PROMOTION_DETAIL(MODULE.PRODUCT, "promotion-detail"),
    PRO_COMBO(MODULE.PRODUCT, "product-combo"),
    PRO_COMBO_DETAIL(MODULE.PRODUCT, "product-combo-detail"),

    SLS_LEDGER(MODULE.SALES, "general-ledger"),
    SLS_LEDGER_TRANS(MODULE.SALES, "ledger-trans"),
    SLS_LEDGER_TRANS_DETAIL(MODULE.SALES, "ledger-trans-detail"),

    STG_MATERIAL(MODULE.STORAGE, "material"),
    STG_TICKET_IMPORT(MODULE.STORAGE, "ticket-import"),
    STG_TICKET_IMPORT_DETAIL(MODULE.STORAGE, "ticket-import-detail"),
    STG_TICKET_EXPORT(MODULE.STORAGE, "ticket-export"),
    STG_TICKET_EXPORT_DETAIL(MODULE.STORAGE, "ticket-export-detail"),
    STG_STORAGE(MODULE.STORAGE, "storage"),
    STG_STORAGE_DETAIL(MODULE.STORAGE, "storage-detail");

    private final MODULE module;
    private final String template;

    Pages(MODULE module, String template) {
        this.module = module;
        this.template = template;
    }

    public String getTemplate() {
        if (this.equals(SYS_LOGIN)) {
            return "/login";
        }
        if (this.equals(PRO_DASHBOARD)) {
            return "/pages/dashboard/dashboard";
        }
        String basePath = "";
        if (module.equals(MODULE.SYSTEM)) {
            basePath = "/pages/system";
        } else if (module.equals(MODULE.PRODUCT)) {
            basePath = "/pages/product";
        } else if (module.equals(MODULE.SALES)) {
            basePath = "/pages/sales/ledger";
        } else if (module.equals(MODULE.STORAGE)) {
            basePath = "/pages/storage";
        }
        return basePath + "/" + template;
    }
}