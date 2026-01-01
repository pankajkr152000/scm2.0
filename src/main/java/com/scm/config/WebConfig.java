package com.scm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @SuppressWarnings("null")
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Chrome DevTools (.well-known)
        registry.addResourceHandler("/.well-known/**")
                .addResourceLocations("classpath:/static/.well-known/");

        // Chrome DevTools (appspecific)
        registry.addResourceHandler("/appspecific/**")
                .addResourceLocations("classpath:/static/appspecific/");
    }
}

