package com.flowiee.app.common.utils;

import com.flowiee.app.system.module.SystemModule;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class FileUtil {
    public static String getExtension(String fileName) {
        String extension = "";
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex > 0 && lastIndex < fileName.length() - 1) {
            extension = fileName.substring(lastIndex + 1);
        }
        return extension;
    }

    public static String pathDirectotyToSave(SystemModule systemModule, String fileName) {
        StringBuilder path = new StringBuilder("\\uploads");
        switch (systemModule) {
            case KHO_TAI_LIEU:
                path.append("\\kho-tai-lieu");
                break;
            case SAN_PHAM:
                path.append("\\san-pham");
                break;
        }
        path.append("\\" + DateUtil.getNamHienTai());
        path.append("\\" + DateUtil.getThangHienTai());
        path.append("\\" + DateUtil.getNgayHienTai());
        path.append("\\" + DateUtil.now("yyyy.MM.dd.HH.mm.ss") + fileName);
        return path.toString();
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
        // Tìm vị trí của dấu "-" cuối cùng
        int lastDashIndex = alias.lastIndexOf("-");
        //Trả về id bằng cách cắt chuỗi từ vị trí cuối cùng của dấu "-" đến cuối chuỗi
        return Integer.parseInt(alias.substring(lastDashIndex + 1));
    }

    public static String getAliasNameFromAliasPath(String alias) {
        // Tìm vị trí của dấu "-" cuối cùng
        int lastDashIndex = alias.lastIndexOf("-");
        //Trả về name bằng cách cắt chuỗi từ đầu đến vị trí cuối cùng của dấu "-"
        return alias.substring(0, lastDashIndex);
    }
}