package com.imooc.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author qingtian
 * @version 1.0
 * @description: 基于 SpringCloud Stream 构建消息驱动微服务
 * @date 2022/5/3 9:03
 */
@EnableDiscoveryClient
@SpringBootApplication
public class StreamClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamClientApplication.class,args);
    }
}
