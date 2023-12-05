package com.flowiee.app.base;

import com.flowiee.app.utils.CommonUtil;

import java.util.Date;

public class StartUp {
    public StartUp() {
        setDefaultValueForCommonUtil();
        setDefaultValueForMessageUtil();
    }

    private void setDefaultValueForCommonUtil() {
        CommonUtil.START_APP_TIME = new Date();
    }

    private void setDefaultValueForMessageUtil() {
    }
}