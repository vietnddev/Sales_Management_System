package com.flowiee.sms.utils;

import com.flowiee.sms.model.MODULE;
import org.apache.commons.lang3.ObjectUtils;
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

public class CommonUtils {
    public static String rootPath = "src/main/resources/static";
    public static String fileUploadPath = rootPath + "/uploads/";
    public static String reportTemplatePath = rootPath + "/report";
    public static String excelTemplatePath = rootPath + "/templates/excel";
    public static String ADMINISTRATOR = "admin";
    public static Date START_APP_TIME = null;

    public static String formatToVND(Object currency) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        currencyFormat.setCurrency(Currency.getInstance("VND"));
        return currency != null ? currencyFormat.format(currency) : "0 VND";
    }

    public static String getMaDonHang() {
        return "F" + CommonUtils.now("yyMMddHHmmss");
    }

    public static String genCategoryCodeByName(String categoryName) {
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

    public static String now(String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    public static String getCategoryType(String key) {
        Map<String, String> map = new HashMap<>();
        for (AppConstants.CATEGORY c : AppConstants.CATEGORY.values()) {
            map.put(c.getKey(), c.getName());
        }
        return map.get(key);
    }

    public static String getExtension(String fileName) {
        String extension = "";
        if (ObjectUtils.isNotEmpty(fileName)) {
            int lastIndex = fileName.lastIndexOf('.');
            if (lastIndex > 0 && lastIndex < fileName.length() - 1) {
                extension = fileName.substring(lastIndex + 1);
            }
        }
        return extension;
    }

    public static String getPathDirectory(MODULE systemModule) {
        try {
            StringBuilder path = new StringBuilder(fileUploadPath);
            switch (systemModule) {
                case PRODUCT:
                    path.append("product");
                    break;
                case CATEGORY:
                    path.append("category");
                    break;
                default:
                	path.append("system");
                	break;
            }
            path.append("/" + DateUtils.getNamHienTai());
            path.append("/" + DateUtils.getThangHienTai());
            path.append("/" + DateUtils.getNgayHienTai());
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

    public static String getPathDirectory(String systemModule) {
        try {
            StringBuilder path = new StringBuilder(fileUploadPath);
            if (MODULE.PRODUCT.name().equals(systemModule)) {
                path.append("product");
            } else if (MODULE.CATEGORY.name().equals(systemModule)) {
                path.append("category");
            }
            path.append("/" + DateUtils.getNamHienTai());
            path.append("/" + DateUtils.getThangHienTai());
            path.append("/" + DateUtils.getNgayHienTai());
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
        String transformedText = "";
        if (text != null) {
            // Loại bỏ dấu tiếng Việt và ký tự đặc biệt
            String normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String textWithoutAccents = pattern.matcher(normalizedText).replaceAll("");
            String cleanedText = textWithoutAccents.replaceAll("[^a-zA-Z0-9 ]", "");

            // Chuyển đổi thành chữ thường (lowercase)
            String lowercaseText = cleanedText.toLowerCase();

            // Thay thế khoảng trắng bằng dấu gạch ngang ("-")
            transformedText = lowercaseText.replace(" ", "-");

            if (transformedText.endsWith("-")) {
                transformedText = transformedText.substring(0, transformedText.length() - 1);
            }
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
        String filePathOriginal = CommonUtils.excelTemplatePath + "/" + templateName + ".xlsx";
        String filePathTemp = CommonUtils.excelTemplatePath + "/" + templateName + "_" + Instant.now(Clock.systemUTC()).toEpochMilli() + ".xlsx";
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

    public static String convertListIntToStr(List<Integer> listId) {
        StringBuilder str = new StringBuilder();
        for (int id : listId) {
            str.append(id).append(",");
        }
        if (!str.isEmpty()) {
            str = new StringBuilder(str.substring(0, str.length() - 1));
        }
        return str.toString();
    }
}