package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.repository.system.FlowieeConfigRepository;
import com.flowiee.pms.service.category.CategoryService;
import com.flowiee.pms.service.system.ConfigService;

import com.flowiee.pms.service.system.LanguageService;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigServiceImpl implements ConfigService {
    private final FlowieeConfigRepository sysConfigRepo;
    private final CategoryService categoryService;
    private final LanguageService languageService;

    @Autowired
    public ConfigServiceImpl(FlowieeConfigRepository sysConfigRepo, CategoryService categoryService, LanguageService languageService) {
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
    public SystemConfig save(SystemConfig systemConfig) {
        return sysConfigRepo.save(systemConfig);
    }

    @Override
    public SystemConfig update(SystemConfig systemConfig, Integer id) {
        systemConfig.setId(id);
        logger.info("Update config success! " + systemConfig.toString());
        return sysConfigRepo.save(systemConfig);
    }

    @Override
    public String delete(Integer id) {
        try {
            sysConfigRepo.deleteById(id);
            return MessageUtils.DELETE_SUCCESS;
        } catch (RuntimeException ex) {
            throw new AppException(ex);
        }
    }

    @Transactional
    @Override
    public String refreshApp() {
        try {
            //
            List<Category> rootCategories = categoryService.findRootCategory();
            for (Category c : rootCategories) {
                if (c.getType() != null && !c.getType().trim().isEmpty()) {
                    AppConstants.CATEGORY.valueOf(c.getType()).setLabel(c.getName());
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
}