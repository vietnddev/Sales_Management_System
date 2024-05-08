package com.flowiee.pms.service.system;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.entity.system.SystemConfig;

import java.util.List;

public interface ConfigService extends BaseService<SystemConfig> {
    String refreshApp();

    List<SystemConfig> getSystemConfigs(String[] configCodes);
}