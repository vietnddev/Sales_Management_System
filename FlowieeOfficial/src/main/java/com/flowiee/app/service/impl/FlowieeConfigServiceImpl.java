package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.FlowieeConfig;
import com.flowiee.app.repository.FlowieeConfigRepository;
import com.flowiee.app.service.system.FlowieeConfigService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowieeConfigServiceImpl implements FlowieeConfigService {
    private static final Logger logger = LoggerFactory.getLogger(FlowieeConfigServiceImpl.class);

    @Autowired
    private FlowieeConfigRepository flowieeConfigRepository;

    @Override
    public FlowieeConfig findById(Integer id) {
        return flowieeConfigRepository.findById(id).orElse(null);
    }

    @Override
    public FlowieeConfig findByKey(String code) {
        return flowieeConfigRepository.findByCode(code);
    }

    @Override
    public List<FlowieeConfig> findAll() {
        return flowieeConfigRepository.findAll();
    }

    @Override
    public String save(FlowieeConfig flowieeConfig) {
        flowieeConfigRepository.save(flowieeConfig);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(FlowieeConfig flowieeConfig, Integer id) {
        flowieeConfig.setId(id);
        flowieeConfigRepository.save(flowieeConfig);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer id) {
        flowieeConfigRepository.deleteById(id);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }
}