package com.flowiee.app.model.system;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public enum SystemModule {
    SAN_PHAM("SẢN PHẨM"),

    THU_VIEN_ANH("THƯ VIỆN ẢNH"),

    KHO_TAI_LIEU("KHO TÀI LIỆU"),

    HE_THONG("HỆ THỐNG"),

    DANH_MUC("DANH MỤC"),


    DASHBOARD("DASHBOARD");

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