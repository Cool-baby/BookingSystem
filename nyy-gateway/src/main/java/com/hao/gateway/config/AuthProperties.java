package com.hao.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 认证系统中需要排除的访问路径
 * @date 2023-11-25 20:51:27
 */
@Data
@ConfigurationProperties(prefix = "nyy.auth")
@Component
public class AuthProperties {

    // 需要认证的路径
    private List<String> includePaths;
    // 直接放行的路径
    private List<String> excludePaths;
}
