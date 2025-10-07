package com.samsung.product_service.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String UPLOAD_DIR = "C:/java/Java Spring Boot/microservice-samsung/uploads/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = Paths.get(UPLOAD_DIR).toUri().toString();
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(path);
    }


}
