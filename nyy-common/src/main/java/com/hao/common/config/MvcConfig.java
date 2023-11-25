package com.hao.common.config;

import com.hao.common.interceptor.UserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Hao
 * @program: hmall
 * @description: MVCConfig
 * @date 2023-11-24 15:29:45
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // 添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInfoInterceptor());
    }
}
