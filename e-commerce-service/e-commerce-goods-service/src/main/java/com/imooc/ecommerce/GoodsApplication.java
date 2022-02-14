package com.imooc.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author qingtian
 * @version 1.0
 * @description: 启动类
 * @date 2022/2/13 21:18
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class GoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class,args);
    }
}
