package com.flowiee.app.service.system;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.FlowieeConfig;

public interface FlowieeConfigService extends BaseService<FlowieeConfig> {
    FlowieeConfig findByKey(String key);
}