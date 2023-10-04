package com.flowiee.app.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class FlowieeUtil {
    public static final String ADMINISTRATOR = "admin";

    public static final String PATH_TEMPLATE_EXCEL = "src/main/resources/static/templates/excel";

    public static String maDonHang() {
        return "F" + DateUtil.now("yyMMddHHmmss");
    }

    public static String ACCOUNT_IP = "unknown";

    public static String getIPAccountLogin() {
//        WebAuthenticationDetails details = null;
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object authDetails = authentication.getDetails();
//            if (authDetails instanceof WebAuthenticationDetails) {
//                details = (WebAuthenticationDetails) authDetails;
//            }
//        }
//        if (details != null) {
//            return details.getRemoteAddress();
//        }
//        return "unknown";

        return ACCOUNT_IP;
    }
}