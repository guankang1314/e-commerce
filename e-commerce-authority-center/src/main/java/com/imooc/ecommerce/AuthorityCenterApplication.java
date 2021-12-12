package com.imooc.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.ecommerce
 * @date 2021/12/4 23:07
 */
@EnableJpaAuditing   //允许jpa自动审计
@SpringBootApplication
@EnableDiscoveryClient
public class AuthorityCenterApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthorityCenterApplication.class,args);
    }
}
