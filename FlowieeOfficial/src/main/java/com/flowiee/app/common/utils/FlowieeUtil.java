package com.flowiee.app.common.utils;

import com.flowiee.app.hethong.entity.Account;
import org.apache.commons.text.StringEscapeUtils;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static Map<String, String> getOrderStatusCategory() {
        Map<String, String> deliveryStatus = new HashMap<>();
        deliveryStatus.put("PREPARING","Đang chuẩn bị");
        deliveryStatus.put("NOT-DELIVERY","Đang chờ đơn vị vận chuyển tiếp nhận");
        deliveryStatus.put("DELIVERING","Đang vận chuyển");
        deliveryStatus.put("COMPLETED","Đã hoàn thành");
        return deliveryStatus;
    }

    public static Map<String, String> getPaymentStatusCategory() {
        Map<String, String> paymentStatus = new HashMap<>();
        paymentStatus.put("UNPAID","Chưa thanh toán");
        paymentStatus.put("PARTLY-PAID","Thanh toán một phần");
        paymentStatus.put("PAID","Đã thanh toán");
        return paymentStatus;
    }

    public static String ACCOUNT_IP = null;
    public static Integer ACCOUNT_ID = null;
    public static String ACCOUNT_USERNAME = null;
    public static Account ACCOUNT = null;
}