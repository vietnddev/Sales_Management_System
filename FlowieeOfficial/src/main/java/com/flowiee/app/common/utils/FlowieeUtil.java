package com.flowiee.app.common.utils;

public class FlowieeUtil {
    public static final String ADMINISTRATOR = "admin";

    public static final String PATH_TEMPLATE_EXCEL = "src/main/resources/static/templates/excel";

    public static String getMaDonHang() {
        return "F" + DateUtil.now("yyMMddHHmmss");
    }

    public static String ACCOUNT_IP = "unknown";
}