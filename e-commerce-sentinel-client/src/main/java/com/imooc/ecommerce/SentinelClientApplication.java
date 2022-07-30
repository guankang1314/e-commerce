package com.imooc.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Guank
 * @version 1.0
 * @description: Sentinel
 * @date 2022-07-17 15:08
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class SentinelClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelClientApplication.class,args);
    }
}
