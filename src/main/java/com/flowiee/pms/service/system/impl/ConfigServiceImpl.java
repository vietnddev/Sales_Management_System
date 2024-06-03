package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.repository.system.ConfigRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.category.CategoryService;
import com.flowiee.pms.service.system.ConfigService;

import com.flowiee.pms.service.system.LanguageService;
import com.flowiee.pms.utils.LogUtils;
import com.flowiee.pms.utils.constants.CategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ConfigServiceImpl extends BaseService implements ConfigService {
    private static final String mainObjectName = "SystemConfig";

    private final ConfigRepository sysConfigRepo;
    private final CategoryService  categoryService;
    private final LanguageService languageService;

    @Autowired
    public ConfigServiceImpl(ConfigRepository sysConfigRepo, CategoryService categoryService, LanguageService languageService) {
        this.sysConfigRepo = sysConfigRepo;
        this.categoryService = categoryService;
        this.languageService = languageService;
    }

    @Override
    public Optional<SystemConfig> findById(Integer id) {
        return sysConfigRepo.findById(id);
    }

    @Override
    public List<SystemConfig> findAll() {
        return sysConfigRepo.findAll();
    }

    @Override
    public SystemConfig update(SystemConfig systemConfig, Integer id) {
        Optional<SystemConfig> configBefore = this.findById(id);
        if (configBefore.isEmpty()) {
            throw new BadRequestException();
        }
        systemConfig.setId(id);
        SystemConfig configUpdated = sysConfigRepo.save(systemConfig);
        Map<String, Object[]> logChanges = LogUtils.logChanges(configBefore, configUpdated);
        systemLogService.writeLogUpdate(MODULE.SYSTEM.name(), ACTION.SYS_CNF_U.name(), mainObjectName, "Cập nhật cấu hình hệ thống", logChanges);
        logger.info("Update config success! {}", systemConfig.toString());
        return configUpdated;
    }

    @Transactional
    @Override
    public String refreshApp() {
        try {
            //
            List<Category> rootCategories = categoryService.findRootCategory();
            for (Category c : rootCategories) {
                if (c.getType() != null && !c.getType().trim().isEmpty()) {
                    CategoryType.valueOf(c.getType()).setLabel(c.getName());
                }
            }
            //
            languageService.reloadMessage("vi");
            languageService.reloadMessage("en");
            //Reload shopInfo

            return "Completed";
        } catch (RuntimeException ex) {
            throw new AppException("An error occurred while refresh app", ex);
        }
    }

    @Override
    public List<SystemConfig> getSystemConfigs(String[] configCodes) {
        return List.of();
    }

    @Override
    public SystemConfig getSystemConfig(String configCode) {
        return sysConfigRepo.findByCode(configCode);
    }

    @Override
    public List<SystemConfig> getSystemConfigs(List<String> configCodes) {
        return sysConfigRepo.findByCode(configCodes);
    }
}