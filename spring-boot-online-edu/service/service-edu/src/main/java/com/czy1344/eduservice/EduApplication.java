package com.czy1344.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 2020/7/25 14:12
 *
 * @author czy1344
 * 说明：启动
 */
@SpringBootApplication
@EnableDiscoveryClient // 开启nacos的注册
@EnableFeignClients // 调用端开启Feign
@ComponentScan(basePackages = {"com.czy1344"})
@MapperScan("com.czy1344.eduservice.mapper")
@EnableScheduling // 开启定时任务
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
