package com.imooc.ecommerce.conf;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Guank
 * @version 1.0
 * @description: 开启服务中的调用保护 对 RestTemplate 包装
 * @date 2022-07-24 7:33
 */
@Slf4j
@Configuration
public class SentinelConfig {

    @Bean
    @SentinelRestTemplate(fallback = "handleFallback", fallbackClass = RestTemplateExceptionUtil.class,
    blockHandler = "handleBlock", blockHandlerClass = RestTemplateExceptionUtil.class)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
