package com.flowiee.pms.service;

import com.flowiee.pms.service.system.SystemLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected SystemLogService systemLogService;
}