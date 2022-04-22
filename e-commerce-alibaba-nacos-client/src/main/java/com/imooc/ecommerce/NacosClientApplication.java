package com.imooc.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.ecommerce
 * @date 2021/12/4 10:57
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@RefreshScope
@EnableCircuitBreaker  //启用 Hysrix
@ServletComponentScan
public class NacosClientApplication {

    public static void main(String[] args) {

        SpringApplication.run(NacosClientApplication.class,args);
    }
}
