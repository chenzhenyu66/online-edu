package com.czy1344.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 2020/8/10 12:49
 *
 * @author czy1344
 * 说明：
 */
@SpringBootApplication
@EnableDiscoveryClient // 注册nacos
@ComponentScan("com.czy1344") // 扫描组件
public class GateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class,args);
    }
}
