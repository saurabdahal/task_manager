package com.casperinv.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        System.out.println(System.getProperty("user.dir"));
        registry
                .addResourceHandler("/upload/**","/static/**")
                .addResourceLocations("file:///D:/uploads/")
                .addResourceLocations("file:///" +
                        System.getProperty("user.dir") + "/src/main/resource/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver());;
    }
}