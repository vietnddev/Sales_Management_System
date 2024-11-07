package com.flowiee.pms.schedule;

import com.flowiee.pms.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public abstract class ScheduleExecutor {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public abstract void execute() throws AppException;
}