package com.czy1344.vodservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 2020/8/2 15:10
 *
 * @author czy1344
 * 说明：
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.czy1344"})
public class VoDApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoDApplication.class,args);
    }
}
