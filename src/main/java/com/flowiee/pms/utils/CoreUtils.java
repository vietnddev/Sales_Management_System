package com.flowiee.pms.utils;

import org.apache.commons.lang3.ObjectUtils;

public class CoreUtils {
    public static final int NUMERIC = 1;
    public static final int STRING = 2;

    public static String trim(String str) {
        if (ObjectUtils.isEmpty(str)) {
            return "";
        }
        str = str.trim();
        if ("null".equals(str)) {
            str = null;
        }
        return str;
    }

    public static String trim(String pvValue, int pvType) {
        if (CoreUtils.isNullStr(pvValue))
        {
            if (pvType == CoreUtils.NUMERIC)
                return "0";
            else if (pvType == CoreUtils.STRING)
                return "";
        }
        return pvValue.trim();
    }

    public static boolean isNullStr(Object pStr)
    {
        return CoreUtils.isNullStr(pStr == null ? null : pStr.toString());
    }

    public static boolean isNullStr(String pStr) {
        if (pStr == null)
            return true;
        else
        {
            String pTrimStr = pStr.trim();
            return pTrimStr.length() == 0 || pTrimStr.equalsIgnoreCase("null");
        }
    }
}