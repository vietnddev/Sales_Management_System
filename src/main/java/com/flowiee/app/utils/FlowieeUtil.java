package com.flowiee.app.utils;

import com.flowiee.app.model.role.SystemModule;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.*;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

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

    public static String getCategoryType(String input) {
        Map<String, String> map = new HashMap<>();
        map.put("unit", AppConstants.UNIT);
        map.put("pay-method", AppConstants.PAYMETHOD);
        map.put("fabric-type", AppConstants.FABRICTYPE);
        map.put("sales-channel", AppConstants.SALESCHANNEL);
        map.put("size", AppConstants.SIZE);
        map.put("color", AppConstants.COLOR);
        map.put("product-type", AppConstants.PRODUCTTYPE);
        map.put("document-type", AppConstants.DOCUMENTTYPE);
        map.put("order-status", AppConstants.ORDERSTATUS);
        map.put("payment-status", AppConstants.PAYMENTSTATUS);
        map.put("brand", AppConstants.BRAND);
        return map.get(input);
    }

    public static String getExtension(String fileName) {
        String extension = "";
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex > 0 && lastIndex < fileName.length() - 1) {
            extension = fileName.substring(lastIndex + 1);
        }
        return extension;
    }

    public static String getPathDirectoty(SystemModule systemModule) {
        try {
            StringBuilder path = new StringBuilder("src/main/resources/static/uploads");
            switch (systemModule) {
                case STORAGE:
                    path.append("/storage");
                    break;
                case PRODUCT:
                    path.append("/product");
                    break;
                case CATEGORY:
                    path.append("/category");
                    break;
                default:
                	path.append("/system");
                	break;
            }
            path.append("/" + FlowieeUtil.getNamHienTai());
            path.append("/" + FlowieeUtil.getThangHienTai());
            path.append("/" + FlowieeUtil.getNgayHienTai());
            File folder = new File(path.toString());
            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    System.out.println("mkdirs OK");
                }
            }
            return path.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String getPathDirectoty(String systemModule) {
        try {
            StringBuilder path = new StringBuilder("src/main/resources/static/uploads");
            if (SystemModule.STORAGE.name().equals(systemModule)) {
                path.append("/storage");
            } else if (SystemModule.PRODUCT.name().equals(systemModule)) {
                path.append("/product");
            } else if (SystemModule.CATEGORY.name().equals(systemModule)) {
                path.append("/category");
            }
            path.append("/" + FlowieeUtil.getNamHienTai());
            path.append("/" + FlowieeUtil.getThangHienTai());
            path.append("/" + FlowieeUtil.getNgayHienTai());
            File folder = new File(path.toString());
            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    System.out.println("mkdirs OK");
                }
            }
            return path.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String getPathDirectotyForImport() {
        try {
            StringBuilder path = new StringBuilder("src/main/resources/static/uploads");
            path.append("/import");
            path.append("/" + FlowieeUtil.getNamHienTai());
            path.append("/" + FlowieeUtil.getThangHienTai());
            path.append("/" + FlowieeUtil.getNgayHienTai());
            File folder = new File(path.toString());
            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    System.out.println("mkdirs OK");
                }
            }
            return path.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String generateAliasName(String text) {
        // Loại bỏ dấu tiếng Việt và ký tự đặc biệt
        String normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String textWithoutAccents = pattern.matcher(normalizedText).replaceAll("");
        String cleanedText = textWithoutAccents.replaceAll("[^a-zA-Z0-9 ]", "");

        // Chuyển đổi thành chữ thường (lowercase)
        String lowercaseText = cleanedText.toLowerCase();

        // Thay thế khoảng trắng bằng dấu gạch ngang ("-")
        String transformedText = lowercaseText.replace(" ", "-");

        if (transformedText.endsWith("-")) {
            transformedText = transformedText.substring(0, transformedText.length() - 1);
        }
        return transformedText;
    }

    public static int getIdFromAliasPath(String alias) {
        return Integer.parseInt(alias.substring(alias.lastIndexOf("-") + 1));
    }

    public static String getAliasNameFromAliasPath(String alias) {
        return alias.substring(0, alias.lastIndexOf("-"));
    }

    public static byte[] exportTemplate(String templateName) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        String filePathOriginal = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + templateName + ".xlsx";
        String filePathTemp = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + templateName + "_" + Instant.now(Clock.systemUTC()).toEpochMilli() + ".xlsx";
        File fileDeleteAfterExport = null;
        try {
            fileDeleteAfterExport = new File(Path.of(filePathTemp).toUri());
            XSSFWorkbook workbook = new XSSFWorkbook(Files.copy(Path.of(filePathOriginal), Path.of(filePathTemp), StandardCopyOption.REPLACE_EXISTING).toFile());
            workbook.write(stream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileDeleteAfterExport.exists()) {
                fileDeleteAfterExport.delete();
            }
        }
        return stream.toByteArray();
    }

    public static XSSFCellStyle setBorder(XSSFCellStyle cellStyle) {
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        return cellStyle;
    }

    public static XSSFCellStyle highlightDataImportEror(XSSFCellStyle cellStyle, XSSFFont fontStyle) {
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        fontStyle.setColor(IndexedColors.RED.getIndex());

        cellStyle.setFont(fontStyle);

        return cellStyle;
    }

    public static Integer getCurrentAccountId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return Integer.parseInt(authentication.getName().substring(authentication.getName().indexOf("_") + 1));
        }
        return null;
    }

    public static String getCurrentAccountUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName().substring(0, authentication.getName().indexOf("_"));
        }
        return null;
    }

    public static String getCurrentAccountIp() {
        WebAuthenticationDetails details = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object authDetails = authentication.getDetails();
            if (authDetails instanceof WebAuthenticationDetails) {
                details = (WebAuthenticationDetails) authDetails;
            }
        }
        return details != null ? details.getRemoteAddress() : "unknown";
    }

    public static Map<String, String> getVoucherType() {
        Map<String, String> voucherTypes = new HashMap<>();
        voucherTypes.put(AppConstants.VOUCHER_TYPE.BOTH.name(), AppConstants.VOUCHER_TYPE.BOTH.getLabel());
        voucherTypes.put(AppConstants.VOUCHER_TYPE.NUMBER.name(), AppConstants.VOUCHER_TYPE.NUMBER.getLabel());
        voucherTypes.put(AppConstants.VOUCHER_TYPE.TEXT.name(), AppConstants.VOUCHER_TYPE.TEXT.getLabel());
        return voucherTypes;
    }
}