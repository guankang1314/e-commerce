package com.imooc.ecommerce.service.communucation;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author qingtian
 * @version 1.0
 * @description: Feign配置类
 * @date 2022/3/17 23:16
 */
@Configuration
public class FeignConfig {

    /**
     * 开启 openFeign 日志
     * @return
     */
    @Bean
    public Logger.Level feignLogger() {
        return Logger.Level.FULL;
    }

    /**
     * OpenFeign 开启重试
     * period: 发起当前请求的时间间隔，单位是 ms
     * maxPeriod : 发起请求的最大时间间隔  单位是 ms
     * maxAttempts : 发起请求的最大次数
     * @return
     */
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100,SECONDS.toMillis(1),5);
    }

    public static final int CONNECT_TIMEOUT_MILLS = 5000;
    public static final int READ_TIMEOUT_MILLS = 5000;

    /**
     * 对请求的链接和响应时间进行限制
     * @return
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(
                CONNECT_TIMEOUT_MILLS, TimeUnit.MILLISECONDS,
                READ_TIMEOUT_MILLS, TimeUnit.MILLISECONDS,
                true
        );
    }
}
