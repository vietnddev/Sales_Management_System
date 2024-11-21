package com.flowiee.pms.schedule;

import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.utils.CoreUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public abstract class ScheduleExecutor {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public abstract void execute() throws AppException;

    public boolean isConfigAvailable(SystemConfig pSystemConfig) {
        if (pSystemConfig == null || CoreUtils.isNullStr(pSystemConfig.getValue())) {
            return false;
        }
        return true;
    }
}