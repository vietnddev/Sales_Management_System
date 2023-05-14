package com.flowiee.app.common.config;

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
                .addResourceLocations("file:/D:/VietND/Project/Flowiee WebApp/FlowieeOfficial/src/main/resources/static/uploads/");
    }
}
