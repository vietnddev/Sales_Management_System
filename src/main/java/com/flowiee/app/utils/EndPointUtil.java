package com.flowiee.app.utils;

public class EndPointUtil {
    public static final String SYS_LOGIN = "/sys/login";
    public static final String SYS_LOGOUT = "/sys/logout";
    public static final String SYS_PROFILE = "/sys/profile";
    public static final String SYS_PROFILE_UPDATE = SYS_PROFILE + "/update";
    public static final String SYS_PROFILE_CHANGEPASSWORD = SYS_PROFILE + "/change-password";
    public static final String SYS_NOTIFICATION = "/sys/notification";
    public static final String SYS_CONFIG = "/sys/config";
    public static final String SYS_LOG = "/sys/log";
    public static final String SYS_ROLE = "/sys/role";

    public static final String SYS_ACCOUNT = "/sys/tai-khoan";

    public static final String CATEGORY = "/system/category";

    public static final String PRO_PRODUCT = "/san-pham";
    public static final String PRO_ORDER = "/don-hang";
    public static final String PRO_CUSTOMER = "/customer";
    public static final String PRO_ORDER_EXPORT = PRO_ORDER + "/export";
    public static final String PRO_SUPPLIER = PRO_PRODUCT + "/supplier";
    public static final String PRO_GALLERY = PRO_PRODUCT + "/gallery";

    public static final String STORAGE = "/storage";
    public static final String STORAGE_DOCUMENT = STORAGE + "/document";
    public static final String STORAGE_MATERIAL = STORAGE + "/material";
    public static final String STORAGE_MATERIAL_TEMPLATE = STORAGE_MATERIAL + "/template";
    public static final String STORAGE_MATERIAL_IMPORT = STORAGE_MATERIAL + "/import";
    public static final String STORAGE_MATERIAL_EXPORT = STORAGE_MATERIAL + "/export";
    public static final String STORAGE_TICKET_IMPORT = STORAGE + "/ticket-import";
    public static final String STORAGE_TICKET_EXPORT = STORAGE + "/ticket-export";
}