package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.config.StartUp;
import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.utils.ChangeLog;
import com.flowiee.pms.utils.constants.*;
import com.flowiee.pms.repository.system.ConfigRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.category.CategoryService;
import com.flowiee.pms.service.system.ConfigService;

import com.flowiee.pms.service.system.LanguageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ConfigServiceImpl extends BaseService implements ConfigService {
    CategoryService mvCategoryService;
    LanguageService mvLanguageService;
    ConfigRepository mvSysConfigRepository;

    @Override
    public Optional<SystemConfig> findById(Long id) {
        return mvSysConfigRepository.findById(id);
    }

    @Override
    public List<SystemConfig> findAll() {
        return mvSysConfigRepository.findAll();
    }

    @Override
    public SystemConfig update(SystemConfig systemConfig, Long id) {
        Optional<SystemConfig> configOpt = this.findById(id);
        if (configOpt.isEmpty()) {
            throw new BadRequestException();
        }
        SystemConfig configBefore = ObjectUtils.clone(configOpt.get());

        systemConfig.setId(id);
        SystemConfig configUpdated = mvSysConfigRepository.save(systemConfig);

        ChangeLog changeLog = new ChangeLog(configBefore, configUpdated);
        systemLogService.writeLogUpdate(MODULE.SYSTEM, ACTION.SYS_CNF_U, MasterObject.SystemConfig, "Cập nhật cấu hình hệ thống", changeLog);
        logger.info("Update config success! {}", configUpdated.getName());

        return configUpdated;
    }

    @Transactional
    @Override
    public String refreshApp() {
        try {
            //
            List<Category> rootCategories = mvCategoryService.findRootCategory();
            for (Category c : rootCategories) {
                if (c.getType() != null && !c.getType().trim().isEmpty()) {
                    CategoryType.valueOf(c.getType()).setLabel(c.getName());
                }
            }
            //
            mvLanguageService.reloadMessage("vi");
            mvLanguageService.reloadMessage("en");
            //Reload shopInfo

            SystemConfig resUploadPathConfigMdl = mvSysConfigRepository.findByCode(ConfigCode.resourceUploadPath.name());
            if (resUploadPathConfigMdl != null) {
                StartUp.mvResourceUploadPath = resUploadPathConfigMdl.getValue();
            }

            int i = 1;
            return new StringBuilder()
                    .append("Completed the following tasks: ")
                    .append("\n " + i++ + ". ").append("Reload message vi & en")
                    .append("\n " + i++ + ". ").append("Reload resource upload path")
                    .toString();
        } catch (RuntimeException ex) {
            throw new AppException("An error occurred while refreshing app configuration", ex);
        }
    }

    @Override
    public List<SystemConfig> getSystemConfigs(String[] configCodes) {
        return List.of();
    }

    @Override
    public SystemConfig getSystemConfig(String configCode) {
        return mvSysConfigRepository.findByCode(configCode);
    }

    @Override
    public List<SystemConfig> getSystemConfigs(List<String> configCodes) {
        return mvSysConfigRepository.findByCode(configCodes);
    }
}