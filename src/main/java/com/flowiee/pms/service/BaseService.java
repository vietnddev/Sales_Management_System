package com.flowiee.pms.service;

import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.service.system.SystemLogService;
import com.flowiee.pms.utils.CoreUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SystemLogService systemLogService;

    public static boolean isConfigAvailable(SystemConfig pSystemConfig) {
        if (pSystemConfig == null || CoreUtils.isNullStr(pSystemConfig.getValue())) {
            return false;
        }
        return true;
    }
}