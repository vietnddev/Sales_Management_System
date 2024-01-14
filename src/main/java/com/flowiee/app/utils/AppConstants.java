package com.flowiee.app.utils;

import lombok.Getter;
import lombok.Setter;

public class AppConstants {
    public static String PRODUCT = "PRODUCT";
    public static String TICKETIMPORT = "TICKETIMPORT";
    public static String TICKETEXPORT = "TICKETEXPORT";
    public static String TEMPLATE_IE_DM_CATEGORY = "Template_IE_DM_Category";
    public static String TEMPLATE_IE_DM_LOAIKICHCO = "Template_IE_DM_LoaiKichCo";
    public static String TEMPLATE_IE_DM_LOAIMAUSAC = "Template_IE_DM_LoaiMauSac";
    public static String TEMPLATE_IE_DM_LOAIKENHBANHANG = "Template_IE_DM_LoaiKenhBanHang";
    public static String TEMPLATE_IE_DM_LOAIHINHTHUCTHANHTOAN = "Template_IE_DM_LoaiHinhThucThanhToan";
    public static String TEMPLATE_IE_DM_LOAIDONVITINH = "Template_IE_DM_LoaiDonViTinh";
    public static String TEMPLATE_IE_DM_LOAISANPHAM = "Template_IE_DM_LoaiSanPham";
    public static String TEMPLATE_IE_DM_LOAITAILIEU = "Template_IE_DM_LoaiTaiLieu";
    public static String TEMPLATE_IE_DM_FABRICTYPE = "Template_IE_DM_FabricType";
    public static String TEMPLATE_I_SANPHAM = "Template_I_DanhSachSanPham";
    public static String TEMPLATE_E_SANPHAM = "Template_E_DanhSachSanPham";

    public static final String EMAIL_HOST = "EMAIL_HOST";
    public static final String EMAIL_PORT = "EMAIL_PORT";
    public static final String EMAIL_USERNAME = "EMAIL_USERNAME";
    public static final String EMAIL_PASSWORD = "EMAIL_PASSWORD";
    public static final String TIMEOUT = "TIMEOUT";
    public static final String PATH_UPLOAD = "PATH_UPLOAD";
    public static final String MAX_SIZE_UPLOAD = "MAX_SIZE_FILE_UPLOAD";
    public static final String EXTENSION_ALLOWED_UPLOAD = "EXTENSION_FILE_ALLOWED_UPLOAD";
    public static final String SEND_MAIL_REPORT_DAILY = "SEND_MAIL_REPORT_DAILY";

    public static final String SERVICE_RESPONSE_SUCCESS = "OK";
    public static final String SERVICE_RESPONSE_FAIL = "NOK";

    @Getter
    public enum PRODUCT_STATUS {
        ACTIVE("Đang kinh doanh"),
        INACTIVE("Ngừng kinh doanh");
        private final String label;

        PRODUCT_STATUS(String label) {
            this.label = label;
        }
    }

    @Getter
    public enum PRICE_STATUS {
        ACTIVE("Đang áp dụng"),
        INACTIVE("Đang không áp dụng");
        private final String label;

        PRICE_STATUS(String label) {
            this.label = label;
        }
    }

    @Getter
    public enum DOCUMENT_TYPE {
        FI("File"),
        FO("Folder");
        private final String label;

        DOCUMENT_TYPE(String label) {
            this.label = label;
        }
    }

    @Getter
    public enum VOUCHER_TYPE {
        TEXT("Chữ"),
        NUMBER("Số"),
        BOTH("Chữ và số");
        private final String label;

        VOUCHER_TYPE(String label) {
            this.label = label;
        }
    }

    @Getter
    public enum VOUCHER_STATUS {
        ACTIVE("Đang áp dụng"),
        INACTIVE("Đang không áp dụng");
        private final String label;

        VOUCHER_STATUS(String label) {
            this.label = label;
        }
    }

    @Getter
    public enum CONTACT_TYPE {
        P("Số điện thoại"),
        E("Email"),
        A("Địa chỉ");
        private final String label;

        CONTACT_TYPE(String label) {
            this.label = label;
        }
    }

    @Getter
    public enum FILE_EXTENSION {
        PNG("png"),
        JPG("jpg"),
        JPEG("jpeg"),
        PDF("pdf"),
        XLS("xls"),
        XLSX("xlsx"),
        PPT("ppt"),
        PPTX("pptx"),
        DOC("doc"),
        DOCX("docx");

        private final String label;

        FILE_EXTENSION(String label) {
            this.label = label;
        }
    }

    @Getter
    public enum CATEGORY {
        SIZE("size", "SIZE", "Kích cỡ"),
        COLOR("color", "COLOR", "Màu sắc"),
        UNIT("unit", "UNIT", "Đơn vị tính"),
        BRAND("brand", "BRAND", "Thương hiệu"),
        FABRIC_TYPE("fabric-type", "FABRIC_TYPE", "Chất liệu vải"),
        PRODUCT_TYPE("product-type", "PRODUCT_TYPE", "Loại sản phẩm"),
        DOCUMENT_TYPE("document-type", "DOCUMENT_TYPE", "Loại tài liệu"),
        SALES_CHANNEL("sales-channel", "SALES_CHANNEL", "Kênh bán hàng"),
        ORDER_STATUS("order-status", "ORDER_STATUS", "Trạng thái đơn hàng"),
        PAYMENT_METHOD("payment-method", "PAYMENT_METHOD", "Hình thức thanh toán"),
        PAYMENT_STATUS("payment-status", "PAYMENT_STATUS", "Trạng thái thanh toán");
        private final String key;
        private final String name;
        @Setter
        private String label;

        CATEGORY(String key, String name, String label) {
            this.key = key;
            this.name = name;
            this.label = label;
        }

    }
}