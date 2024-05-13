package com.flowiee.pms.service.system;

import com.flowiee.pms.service.CrudService;
import com.flowiee.pms.entity.system.SystemConfig;

import java.util.List;

public interface ConfigService extends CrudService<SystemConfig> {
    String refreshApp();

    List<SystemConfig> getSystemConfigs(String[] configCodes);

    SystemConfig getSystemConfig(String configCode);

    List<SystemConfig> getSystemConfigs(List<String> configCodes);
}