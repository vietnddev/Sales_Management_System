package com.flowiee.app.common.action;

public enum DashboardAction {
    READ_DASHBOARD("Xem dashboard");

    DashboardAction(String label) {
        this.label = label;
    }

    private final String label;

    public String getLabel() {
        return label;
    }
}