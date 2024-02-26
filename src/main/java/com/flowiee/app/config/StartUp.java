package com.flowiee.app.config;

import com.flowiee.app.entity.SystemLog;
import com.flowiee.app.repository.SystemLogRepository;
import com.flowiee.app.utils.CommonUtils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartUp {
	
	private static final Logger log = LoggerFactory.getLogger(StartUp.class);
	
    public StartUp() {
        setDefaultValueForCommonUtil();
        setDefaultValueForMessageUtil();
    }

    private void setDefaultValueForCommonUtil() {
        CommonUtils.START_APP_TIME = new Date();
    }

    private void setDefaultValueForMessageUtil() {
    }
    
    @Bean
    CommandLineRunner init(SystemLogRepository repository) {
    	//return args -> {
        	//Do something...
        //};
    	return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
            	//Load system config
            }
        };     
    }
}