package com.flowiee.app.config;

import com.flowiee.app.utils.CommonUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/uploads/**")
            .addResourceLocations("file:/" + System.getProperty("user.dir") + "/" + CommonUtil.fileUploadPath)
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
    }
}