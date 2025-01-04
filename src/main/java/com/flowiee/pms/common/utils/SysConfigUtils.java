package com.flowiee.pms.common.utils;

import com.flowiee.pms.entity.system.SystemConfig;

public class SysConfigUtils {
    public static boolean isValid(SystemConfig pSystemConfig) {
        if (pSystemConfig == null || CoreUtils.isNullStr(pSystemConfig.getValue())) {
            return false;
        }
        return true;
    }

    public static boolean isYesOption(SystemConfig pSystemConfig) {
        return isValid(pSystemConfig) && "Y".equals(CoreUtils.trim(pSystemConfig.getValue()));
    }

    public int getIntValue(SystemConfig pSystemConfig) {
        if (isValid(pSystemConfig)) {
            String lvValue = CoreUtils.trim(pSystemConfig.getValue());
            if (CoreUtils.isNumeric(lvValue)) {
                return Integer.parseInt(lvValue);
            }
        }
        throw new IllegalArgumentException();
    }
}