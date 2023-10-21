package com.flowiee.app.hethong.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.hethong.entity.FlowieeConfig;

public interface FlowieeConfigService extends BaseService<FlowieeConfig> {
    FlowieeConfig findByKey(String key);

    void defaultConfig();
}