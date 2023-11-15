package com.flowiee.app.common.utils;

import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import com.flowiee.app.entity.Account;

public class FlowieeUtil {
    public static final Integer SYS_NOTI_ID = 0;
    public static final String ADMINISTRATOR = "admin";
    public static final String PATH_TEMPLATE_EXCEL = "src/main/resources/static/templates/excel";

    public static final String CATEGORY = "";

    public static Date convertStringToDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error occurred while parsing date: " + e.getMessage());
        }
        return date;
    }

    public static Date convertStringToDate(String dateString, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error occurred while parsing date: " + e.getMessage());
        }
        return date;
    }

    public static Date formatDate(Date date, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String formattedDateStr = sdf.format(date);
            return sdf.parse(formattedDateStr);
        } catch (ParseException e) {
            System.out.println(e.getCause());
            e.printStackTrace();
            return date;
        }
    }

    public static String formatToVND(Object currency) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        currencyFormat.setCurrency(Currency.getInstance("VND"));
        return currency != null ? currencyFormat.format(currency) : "0 VND";
    }

    public static String getMaDonHang() {
        return "F" + FlowieeUtil.now("yyMMddHHmmss");
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

    public static String getNamHienTai() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    public static String getThangHienTai() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    public static String getNgayHienTai() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    public static Map<String, String> getPaymentStatusCategory() {
        Map<String, String> paymentStatus = new HashMap<>();
        paymentStatus.put("UNPAID","Chưa thanh toán");
        paymentStatus.put("PARTLY-PAID","Thanh toán một phần");
        paymentStatus.put("PAID","Đã thanh toán");
        return paymentStatus;
    }

    public static String now(String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    public static String ACCOUNT_IP = null;
    public static Integer ACCOUNT_ID = null;
    public static String ACCOUNT_USERNAME = null;
    public static Account ACCOUNT = null;
}