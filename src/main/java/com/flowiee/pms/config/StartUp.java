package com.flowiee.pms.config;

import com.flowiee.pms.model.ShopInfo;
import com.flowiee.pms.service.system.ConfigService;
import com.flowiee.pms.service.system.LanguageService;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.CommonUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartUp {
	private final LanguageService languageService;
	private final ConfigService configService;

	public StartUp(LanguageService languageService, ConfigService configService) {
		this.languageService = languageService;
		this.configService = configService;
	}
	
    @Bean
    CommandLineRunner init() {
    	return args -> {
            CommonUtils.START_APP_TIME = LocalDateTime.now();
            loadLanguageMessages("en");
            loadLanguageMessages("vi");
			loadShopInfo();
            initEndPointConfig();
			initReportConfig();
        };
    }
    
    private void loadLanguageMessages(String langCode) {
        languageService.reloadMessage(langCode);
    }

	private void loadShopInfo() {
		configService.getSystemConfigs(new String[] {});
		//Load info from database
		ShopInfo shopInfo = new ShopInfo();
		shopInfo.setName("");
		shopInfo.setEmail("");
		shopInfo.setPhoneNumber("");
		shopInfo.setAddress("");
		CommonUtils.mvShopInfo = shopInfo;
	}

	private void initEndPointConfig() {
		CommonUtils.mvEndPointHeaderConfig.clear();
		CommonUtils.mvEndPointSideBarConfig.clear();
		for (AppConstants.ENDPOINT e : AppConstants.ENDPOINT.values()) {
			if (e.getType().equals("HEADER") && e.isStatus()) {
				CommonUtils.mvEndPointHeaderConfig.put(e.name(), e.getValue());
			}
			 if (e.getType().equals("SIDEBAR") && e.isStatus()) {
				CommonUtils.mvEndPointSideBarConfig.put(e.name(), e.getValue());
			}
		}
    }

	private void initReportConfig() {
		String templateExportTempStr = CommonUtils.excelTemplatePath + "/temp";
		Path templateExportTempPath = Paths.get(templateExportTempStr);
		if (!Files.exists(templateExportTempPath)) {
            try {
                Files.createDirectory(templateExportTempPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
	}
}