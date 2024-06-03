package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum TemplateExport {
    LIST_OF_PRODUCTS("Template_E_Product.xlsx", "E"),
    LIST_OF_ORDERS("Template_E_Order.xlsx", "E"),
    LIST_OF_CATEGORIES("Template_IE_DM_Category.xlsx", "E"),
    STORAGE_ITEMS("Template_E_Storage.xlsx", "E"),
    LEDGER_TRANSACTIONS("Template_E_GeneralLedger.xlsx", "E");

    private final String templateName;
    private final String type;

    TemplateExport(String templateName, String type) {
        this.templateName = templateName;
        this.type = type;
    }
}