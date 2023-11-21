package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.FlowieeConfig;

public interface FlowieeConfigService extends BaseService<FlowieeConfig> {
    FlowieeConfig findByKey(String key);
}