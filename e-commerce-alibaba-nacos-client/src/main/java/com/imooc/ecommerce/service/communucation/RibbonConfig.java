package com.imooc.ecommerce.service.communucation;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author qingtian
 * @version 1.0
 * @description: 使用 Ribbon 之前的配置，增强 RestTemplate
 * @date 2022/3/15 23:53
 */
@Component
public class RibbonConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
