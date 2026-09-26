package com.bravapro.core.infrastructure.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class PermissionWebConfig implements WebMvcConfigurer {

    private final QueryPermissionInterceptor queryPermissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(queryPermissionInterceptor).addPathPatterns("/api/**");
    }
}
