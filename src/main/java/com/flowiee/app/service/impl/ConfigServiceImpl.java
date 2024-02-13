package com.flowiee.app.service.impl;

import com.flowiee.app.entity.FlowieeConfig;
import com.flowiee.app.repository.FlowieeConfigRepository;
import com.flowiee.app.service.ConfigService;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {
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
    public FlowieeConfig save(FlowieeConfig flowieeConfig) {
        return flowieeConfigRepository.save(flowieeConfig);
    }

    @Override
    public FlowieeConfig update(FlowieeConfig flowieeConfig, Integer id) {
        flowieeConfig.setId(id);
        logger.info("Update config success! " + flowieeConfig.toString());
        return flowieeConfigRepository.save(flowieeConfig);
    }

    @Override
    public String delete(Integer id) {
        flowieeConfigRepository.deleteById(id);
        return MessageUtils.DELETE_SUCCESS;
    }
}