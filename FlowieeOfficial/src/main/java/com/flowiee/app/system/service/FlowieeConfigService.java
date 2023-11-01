package com.flowiee.app.system.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.system.entity.FlowieeConfig;

public interface FlowieeConfigService extends BaseService<FlowieeConfig> {
    FlowieeConfig findByKey(String key);

    void defaultConfig();
}