package com.flowiee.app.common.action;

public enum SupplierAction {
    READ_SUPPLIER("Read supplier"),
    CREATE_SUPPLIER("Create supplier"),
    UPDATE_SUPPLIER("Update supplier"),
    DELETE_SUPPLIER("Delete supplier");

    SupplierAction(String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}