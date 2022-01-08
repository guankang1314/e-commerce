package com.imooc.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author qingtian
 * @version 1.0
 * @description: 用户账户微服务启动入口
 * @date 2022/1/8 23:09
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class,args);
    }
}
