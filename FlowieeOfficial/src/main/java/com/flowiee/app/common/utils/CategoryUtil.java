package com.flowiee.app.common.utils;

import java.util.HashMap;
import java.util.Map;

public class CategoryUtil {
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

    public static String getCategoryType(String input) {
        Map<String, String> map = new HashMap<>();
        map.put("unit", UNIT);
        map.put("pay-method", PAYMETHOD);
        map.put("fabric-type", FABRICTYPE);
        map.put("sales-channel", SALESCHANNEL);
        map.put("size", SIZE);
        map.put("color", COLOR);
        map.put("product-type", PRODUCTTYPE);
        map.put("document-type", DOCUMENTTYPE);
        map.put("order-status", ORDERSTATUS);
        map.put("payment-status", PAYMENTSTATUS);
        return map.get(input);
    }
}