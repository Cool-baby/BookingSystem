package com.hao.maneuver.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: Redisson配置
 * @date 2023-11-11 18:11:11
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String ip;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    // 配置RedissonClient
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        // 配置单节点模式，设置地址、密码
        String address = "redis://" + ip + ":" + port;
        config.useSingleServer().setAddress(address).setPassword(password);
        return Redisson.create(config);
    }
}
