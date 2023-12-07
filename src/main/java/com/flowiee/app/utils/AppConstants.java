package com.flowiee.app.utils;

public class AppConstants {
    public static String TICKETIMPORT = "TICKETIMPORT";
    public static String TICKETEXPORT = "TICKETEXPORT";
    public static String PRODUCT = "PRODUCT";
    public static String UNIT = "UNIT";
    public static String PAYMETHOD = "PAYMETHOD";
    public static String FABRICTYPE = "FABRICTYPE";
    public static String SALESCHANNEL = "SALESCHANNEL";
    public static String SIZE = "SIZE";
    public static String COLOR = "COLOR";
    public static String PRODUCTTYPE = "PRODUCTTYPE";
    public static String DOCUMENTTYPE = "DOCUMENTTYPE";
    public static String ORDERSTATUS = "ORDERSTATUS";
    public static String PAYMENTSTATUS = "PAYMENTSTATUS";
    public static String BRAND = "BRAND";

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

    public enum PRODUCT_STATUS {
        A("Đang áp dụng"),
        I("Đang không áp dụng");
        private final String label;

        PRODUCT_STATUS(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public enum PRICE_STATUS {
        A("Đang áp dụng"),
        I("Đang không áp dụng");
        private final String label;

        PRICE_STATUS(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public enum DOCUMENT_TYPE {
        FI("File"),
        FO("Folder");
        private final String label;

        DOCUMENT_TYPE(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public enum VOUCHER_TYPE {
        TEXT("Chữ"),
        NUMBER("Số"),
        BOTH("Chữ và số");
        private final String label;

        VOUCHER_TYPE(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public enum VOUCHER_STATUS {
        ACTIVE("Đang áp dụng"),
        INACTIVE("Đang không áp dụng");
        private final String label;

        VOUCHER_STATUS(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}