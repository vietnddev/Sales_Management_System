package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.config.StartUp;
import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.ShopInfo;
import com.flowiee.pms.utils.ChangeLog;
import com.flowiee.pms.utils.CommonUtils;
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
        ShopInfo lvShopInfo = CommonUtils.mvShopInfo != null ? CommonUtils.mvShopInfo : new ShopInfo();
        try {
            //Reload system configs
            List<SystemConfig> systemConfigList = this.findAll();
            StartUp.mvSystemConfigList.clear();
            for (SystemConfig systemConfig : systemConfigList) {
                ConfigCode lvConfigCode = ConfigCode.valueOf(systemConfig.getCode());
                String lvConfigValue = systemConfig.getValue();

                if (lvConfigCode == null) continue;

                if (ConfigCode.resourceUploadPath.equals(lvConfigCode)) StartUp.mvResourceUploadPath = lvConfigValue;
                if (ConfigCode.shopName.equals(lvConfigCode))           lvShopInfo.setName(lvConfigValue);
                if (ConfigCode.shopPhoneNumber.equals(lvConfigCode))    lvShopInfo.setPhoneNumber(lvConfigValue);
                if (ConfigCode.shopEmail.equals(lvConfigCode))          lvShopInfo.setEmail(lvConfigValue);
                if (ConfigCode.shopAddress.equals(lvConfigCode))        lvShopInfo.setAddress(lvConfigValue);
                if (ConfigCode.shopLogoUrl.equals(lvConfigCode))        lvShopInfo.setLogoUrl(lvConfigValue);

                StartUp.mvSystemConfigList.put(lvConfigCode, systemConfig);
            }
            CommonUtils.mvShopInfo = lvShopInfo;

            //Reload category label
            List<Category> rootCategories = mvCategoryService.findRootCategory();
            for (Category c : rootCategories) {
                if (c.getType() != null && !c.getType().trim().isEmpty()) {
                    CategoryType.valueOf(c.getType()).setLabel(c.getName());
                }
            }

            //Reload order status
            List<Category> lvOrderStatusList = mvCategoryService.findOrderStatus(null);
            for (Category lvCategory : lvOrderStatusList) {
                if (Category.ROOT_LEVEL.equals(lvCategory.getCode()))
                    continue;
                OrderStatus lvOrderStatus = OrderStatus.get(lvCategory.getCode());
                if (lvOrderStatus != null) {
                    lvOrderStatus.setDescription(lvCategory.getNote());
                }
            }

            //Reload message
            mvLanguageService.reloadMessage("vi");
            mvLanguageService.reloadMessage("en");

            int i = 1;
            return new StringBuilder()
                    .append("Completed the following tasks: ")
                    .append("\n " + i++ + ". ").append("Reload message vi & en")
                    .append("\n " + i++ + ". ").append("Reload system configs")
                    .toString();
        } catch (RuntimeException ex) {
            throw new AppException("An error occurred while refreshing app configuration", ex);
        }
    }

    @Override
    public List<SystemConfig> getSystemConfigs(String[] configCodes) {
        return getSystemConfigs(List.of(configCodes));
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