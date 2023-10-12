package com.flowiee.app.hethong.service;

import com.flowiee.app.hethong.entity.FlowieeConfig;

import java.util.List;

public interface FlowieeConfigService {
    FlowieeConfig findById(Integer id);

    FlowieeConfig findByKey(String key);

    List<FlowieeConfig> findAll();

    String save(FlowieeConfig flowieeConfig);

    String update(FlowieeConfig flowieeConfig, Integer id);

    String delete(Integer id);

    void defaultConfig();
}