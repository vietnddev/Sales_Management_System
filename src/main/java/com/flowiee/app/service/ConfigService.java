package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.FlowieeConfig;

import java.util.List;

public interface ConfigService extends BaseService<FlowieeConfig> {
    List<FlowieeConfig> findAll();

    FlowieeConfig findByKey(String key);
}