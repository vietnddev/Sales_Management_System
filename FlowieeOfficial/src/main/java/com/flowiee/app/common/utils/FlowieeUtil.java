package com.flowiee.app.common.utils;

import com.flowiee.app.hethong.entity.Account;
import org.apache.commons.text.StringEscapeUtils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class FlowieeUtil {
    public static final Integer SYS_NOTI_ID = 0;
    public static final String ADMINISTRATOR = "admin";
    public static final String PATH_TEMPLATE_EXCEL = "src/main/resources/static/templates/excel";

    public static String getMaDonHang() {
        return "F" + DateUtil.now("yyMMddHHmmss");
    }

    public static String getMaDanhMuc(String categoryName) {
        String normalized = Normalizer.normalize(categoryName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("[^\\p{ASCII}]");
        String strTemp = pattern.matcher(normalized).replaceAll("").replaceAll(" ", "").toUpperCase();

        StringBuilder result = new StringBuilder();
        for (char c : strTemp.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            }
        }

        return result.toString();
    }

    public static String ACCOUNT_IP = null;
    public static Integer ACCOUNT_ID = null;
    public static String ACCOUNT_USERNAME = null;
    public static Account ACCOUNT = null;
}