package com.flowiee.pms.config;

import com.flowiee.pms.utils.FileUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            //.addResourceHandler("/uploads/**")
            .addResourceHandler(StartUp.appConfig.getFileUploadPath() + "/**")
            .addResourceLocations("file:/" + System.getProperty("user.dir") + "/" + StartUp.appConfig.getFileUploadPath())
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
    }
}