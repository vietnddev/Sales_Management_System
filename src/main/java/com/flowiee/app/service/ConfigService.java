package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.SystemConfig;

import java.util.List;

public interface ConfigService extends BaseService<SystemConfig> {
    List<SystemConfig> findAll();

    String refreshApp();
}