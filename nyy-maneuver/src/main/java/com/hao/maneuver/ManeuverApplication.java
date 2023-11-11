package com.hao.maneuver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 活动应用
 * @date 2023-11-09 11:40:40
 */
@SpringBootApplication
@MapperScan(value = {"com.hao.common.mapper", "com.hao.maneuver.mapper"})
@ComponentScan(value = {"com.hao.common", "com.hao.maneuver"})
public class ManeuverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManeuverApplication.class, args);
    }
}
