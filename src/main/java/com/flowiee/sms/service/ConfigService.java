package com.flowiee.sms.service;

import com.flowiee.sms.core.BaseService;
import com.flowiee.sms.entity.SystemConfig;

import java.util.List;

public interface ConfigService extends BaseService<SystemConfig> {
    List<SystemConfig> findAll();

    String refreshApp();
}