package com.imooc.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author qingtian
 * @version 1.0
 * @description: HystrixDashboard 入口
 * 127.0.0.1:9999/ecommerce-hystrix-dashboard/hystrix
 * @date 2022/4/26 23:19
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrixDashboard     //开启 hystrix dashboard 注解
public class HystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplication.class,args);
    }
}
