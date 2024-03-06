package com.flowiee.sms.core.config;

import com.flowiee.sms.service.LanguageService;
import com.flowiee.sms.utils.CommonUtils;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartUp {
	private final LanguageService languageService;

	public StartUp(LanguageService languageService) {
		this.languageService = languageService;
	}
	
    @Bean
    CommandLineRunner init() {
    	return args -> {
            CommonUtils.START_APP_TIME = new Date();
            loadLanguageMessages("en");
            loadLanguageMessages("vi");
        };
    }
    
    private void loadLanguageMessages(String langCode) {
        languageService.reloadMessage(langCode);
    }
}