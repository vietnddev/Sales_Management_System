package com.flowiee.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                // Địa chỉ tài nguyên tĩnh
                .addResourceHandler("/uploads/**")
                // Thư mục lưu trữ
                .addResourceLocations("file:/E:/FLOWIEE/SpringBoot/FlowieeOfficial/src/main/resources/static/uploads/");
    }
}
