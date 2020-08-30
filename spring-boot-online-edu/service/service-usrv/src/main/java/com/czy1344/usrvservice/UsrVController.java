package com.czy1344.usrvservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 2020/8/4 19:23
 *
 * @author czy1344
 * 说明：用户使用界面
 */
@SpringBootApplication()
@ComponentScan(basePackages = "com.czy1344")
@EnableDiscoveryClient // 开启nacos的注册
@EnableFeignClients // 调用端开启Feign
@MapperScan("com.czy1344.usrvservice.mapper")
public class UsrVController {
    public static void main(String[] args) {
        SpringApplication.run(UsrVController.class,args);
    }
}
