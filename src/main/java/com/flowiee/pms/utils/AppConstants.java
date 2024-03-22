package com.flowiee.pms.utils;

import lombok.Getter;
import lombok.Setter;

public class AppConstants {
    public static String TEMPLATE_IE_DM_CATEGORY = "Template_IE_DM_Category";
    public static String TEMPLATE_IE_DM_LOAIDONVITINH = "Template_IE_DM_LoaiDonViTinh";
    public static String TEMPLATE_I_SANPHAM = "Template_I_DanhSachSanPham";
    public static String TEMPLATE_E_SANPHAM = "Template_E_DanhSachSanPham";

    @Getter
    public enum PRODUCT_STATUS {
        A("Đang kinh doanh"),
        I("Ngừng kinh doanh");
        private final String label;

        PRODUCT_STATUS(String label) {
            this.label = label;
        }
    }

    @Getter
    public enum PRICE_STATUS {
        A("Đang áp dụng"),
        I("Đang không áp dụng");
        private final String label;

        PRICE_STATUS(String label) {
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
        A("Đang áp dụng"),
        I("Đang không áp dụng");
        private final String label;

        VOUCHER_STATUS(String label) {
            this.label = label;
        }
    }

    @Getter
    public enum TICKET_EX_STATUS {
        DRAFT("Lưu nháp"),
        COMPLETED("Hoàn thành"),
        CANCEL("Hủy");

        private final String label;

        TICKET_EX_STATUS(String label) {
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
        PAYMENT_STATUS("payment-status", "PAYMENT_STATUS", "Trạng thái thanh toán"),
        COMMUNE("commune", "COMMUNE", "Xã"),
        DISTRICT("district", "DISTRICT", "Huyện"),
        PROVINCE("province", "PROVINCE", "Tỉnh");

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

    @Getter
    public enum DASHBOARD_ACTION {
        READ_DASHBOARD("Xem dashboard");

        DASHBOARD_ACTION(String label) {
            this.label = label;
        }

        private final String label;
    }
}