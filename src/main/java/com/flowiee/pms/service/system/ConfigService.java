package com.flowiee.pms.service.system;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.entity.system.SystemConfig;

public interface ConfigService extends BaseService<SystemConfig> {
    String refreshApp();
}