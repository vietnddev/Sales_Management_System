package com.flowiee.app.common.utils;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.hethong.model.module.SystemModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class FileUtil {
    public static String rootPath = "src/main/resources/static/";

    public static String TEMPLATE_DM_LOAIKICHCO = "Template_IE_DM_LoaiKichCo";
    public static String TEMPLATE_DM_LOAIMAUSAC = "Template_IE_DM_LoaiMauSac";
    public static String TEMPLATE_DM_LOAIKENHBANHANG = "Template_IE_DM_LoaiKenhBanHang";
    public static String TEMPLATE_DM_LOAIHINHTHUCTHANHTOAN = "Template_IE_DM_LoaiHinhThucThanhToan";
    public static String TEMPLATE_DM_LOAIDONVITINH = "Template_IE_DM_LoaiDonViTinh";
    public static String TEMPLATE_DM_LOAISANPHAM = "Template_IE_DM_LoaiSanPham";
    public static String TEMPLATE_DM_LOAITAILIEU = "Template_IE_DM_LoaiTaiLieu";
    public static String TEMPLATE_SANPHAM = "Template_E_DanhSachSanPham";

    public static String getExtension(String fileName) {
        String extension = "";
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex > 0 && lastIndex < fileName.length() - 1) {
            extension = fileName.substring(lastIndex + 1);
        }
        return extension;
    }

    public static String pathDirectoty(SystemModule systemModule) {
        try {
            StringBuilder path = new StringBuilder("src/main/resources/static/uploads");
            switch (systemModule) {
                case KHO_TAI_LIEU:
                    path.append("/kho-tai-lieu");
                    break;
                case SAN_PHAM:
                    path.append("/san-pham");
                    break;
                default:
                    throw new NotFoundException();
            }
            path.append("/" + DateUtil.getNamHienTai());
            path.append("/" + DateUtil.getThangHienTai());
            path.append("/" + DateUtil.getNgayHienTai());
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