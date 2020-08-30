package com.czy1344.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 2020/8/10 14:53
 *
 * @author czy1344
 * 说明：
 */
@SpringBootApplication
@EnableDiscoveryClient // 开启nacos的注册
@ComponentScan(basePackages = {"com.czy1344"})
@MapperScan("com.czy1344.aclservice.mapper")
public class AclApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class,args);
    }
}
