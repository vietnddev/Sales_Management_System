package com.flowiee.app.model.role;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public enum SystemModule {
    PRODUCT("SẢN PHẨM"),

    STORAGE("KHO TÀI LIỆU"),

    SYSTEM("HỆ THỐNG"),

    CATEGORY("DANH MỤC");

    private final String label;

    SystemModule(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static SystemModule valueOfLabel(String label) {
        for (SystemModule e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }

    public static List<String> getAllValue() {
        List<String> listValue = new ArrayList<>();
        for (SystemModule e : values()) {
            listValue.add(e.label);
        }
        return listValue;
    }

    public static LinkedHashMap<String, String> getAll() {
        LinkedHashMap<String, String> hm = new LinkedHashMap<>();
        for (SystemModule e : values()) {
            hm.put(e.name(), e.label);
        }
        return hm;
    }
}