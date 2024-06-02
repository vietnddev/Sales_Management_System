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
    public enum PROMOTION_STATUS {
        A("Đang áp dụng"),
        I("Đang không áp dụng");
        private final String label;

        PROMOTION_STATUS(String label) {
            this.label = label;
        }
    }

    @Getter
    public enum TICKET_IM_STATUS {
        DRAFT("Lưu nháp"),
        COMPLETED("Hoàn thành"),
        CANCEL("Hủy");

        private final String label;

        TICKET_IM_STATUS(String label) {
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
        COLLECTION("collection", "COLLECTION", "Bộ sưu tập"),
        ORDER_TYPE("order-type", "ORDER_TYPE", "Loại đơn hàng"),
        FABRIC_TYPE("fabric-type", "FABRIC_TYPE", "Chất liệu vải"),
        PRODUCT_TYPE("product-type", "PRODUCT_TYPE", "Loại sản phẩm"),
        RECEIPT_TYPE("receipt-type", "RECEIPT_TYPE", "Loại phiếu thu"),
        PAYMENT_TYPE("payment-type", "PAYMENT_TYPE", "Loại phiếu chi"),
        DOCUMENT_TYPE("document-type", "DOCUMENT_TYPE", "Loại tài liệu"),
        SALES_CHANNEL("sales-channel", "SALES_CHANNEL", "Kênh bán hàng"),
        SHIP_METHOD("ship-method", "SHIP_METHOD", "Hình thức vận chuyển"),
        ORDER_STATUS("order-status", "ORDER_STATUS", "Trạng thái đơn hàng"),
        GROUP_CUSTOMER("group-customer", "GROUP_CUSTOMER", "Nhóm khách hàng"),
        GROUP_OBJECT("group-object", "GROUP_OBJECT", "Nhóm đối tượng/ đối tác"),
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

    @Getter
    public enum ENDPOINT {
        URL_PROFILE("/sys/profile", "HEADER", true),
        URL_LOGIN("/sys/login", "HEADER", true),
        URL_LOGOUT("/sys/logout", "HEADER", true),
        URL_NOTIFICATION("/sys/notification", "HEADER", true),

        URL_CATEGORY("/system/category", "SIDEBAR", true),
        URL_PRODUCT("/san-pham", "SIDEBAR", true),
        URL_PRODUCT_ORDER("/order", "SIDEBAR", true),
        URL_PRODUCT_CREATE_ORDER("/order/ban-hang", "SIDEBAR", true),
        URL_PRODUCT_CUSTOMER("/customer", "SIDEBAR", true),
        URL_PRODUCT_SUPPLIER("/san-pham/supplier", "SIDEBAR", true),
        URL_PRODUCT_GALLERY("/gallery", "SIDEBAR", true),
        URL_PRODUCT_VOUCHER("/san-pham/voucher", "SIDEBAR", true),
        URL_PRODUCT_PROMOTION("/promotion", "SIDEBAR", true),
        URL_SALES_LEDGER("/ledger", "SIDEBAR", true),
        URL_SALES_LEDGER_RECEIPT("/ledger/receipt", "SIDEBAR", true),
        URL_SALES_LEDGER_PAYMENT("/ledger/payment", "SIDEBAR", true),
        URL_STG_DASHBOARD("/stg", "SIDEBAR", true),
        URL_STG_DOCUMENT("/stg/doc", "SIDEBAR", true),
        URL_STG_MATERIAL("/stg/material", "SIDEBAR", true),
        URL_STG_TICKET_IMPORT("/stg/ticket-import", "SIDEBAR", true),
        URL_STG_TICKET_EXPORT("/stg/ticket-export", "SIDEBAR", true),
        URL_SYS_CONFIG("/sys/config", "SIDEBAR", true),
        URL_SYS_LOG("/sys/log", "SIDEBAR", true),
        URL_SYS_GR_ACCOUNT("/sys/group-account", "SIDEBAR", true),
        URL_SYS_ACCOUNT("/sys/tai-khoan", "SIDEBAR", true),
        URL_STG_STORAGE("/storage", "SIDEBAR", true),

        URL_STG_MATERIAL_IMPORT("/stg/material/import", "MAIN", true),
        URL_STG_MATERIAL_IMPORT_TEMPLATE( "/stg/material/template", "MAIN", true),
        URL_STG_MATERIAL_EXPORT("/stg/material/export", "MAIN", true);

        private final String value;
        private final String type;
        private final boolean status;

        ENDPOINT(String value, String type, boolean status) {
            this.value = value;
            this.type = type;
            this.status = status;
        }
    }

    @Getter
    public enum CUSTOMER_GROUP {
        G1("Khách mua hàng vãng lai"),
        G2("khách hàng thân thiết"),
        G3("Khách hàng VIP");

        CUSTOMER_GROUP(String label) {
            this.label = label;
        }

        private final String label;
    }

    @Getter
    public enum STORAGE_STATUS {
        A("Đang được sử dụng"),
        I("Không sử dụng");
        private final String label;

        STORAGE_STATUS(String label) {
            this.label = label;
        }
    }

    @Getter
    public enum PRICE_TYPE {
        L("Giá bán lẻ"),
        S("Giá sỉ");
        private final String label;

        PRICE_TYPE(String label) {
            this.label = label;
        }
    }
}