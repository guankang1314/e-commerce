package com.imooc.ecommerce.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qingtian
 * @version 1.0
 * @description: 配置登录请求转发规则
 * @date 2021/12/25 23:58
 */
@Configuration
public class RouteLocatorConfig {

    /**
     * @description:  使用代码定义路由规则，在网关层面上拦截登录和注册接口
     * @param: builder
     * @return: org.springframework.cloud.gateway.route.RouteLocator
     * @date: 2021/12/26 0:00
     */
    @Bean
    public RouteLocator loginRouteLocator(RouteLocatorBuilder builder) {
        //手动定义Gateway路由规则需要指定id,path 和url
        return builder.routes()
                .route(
                        "e_commerce_authority",
                        r -> r.path(
                                "/imooc/e-commerce/login",
                                "/imooc/e-commerce/register"
                        ).uri("http://localhost:9001/")
                ).build();
    }
}
