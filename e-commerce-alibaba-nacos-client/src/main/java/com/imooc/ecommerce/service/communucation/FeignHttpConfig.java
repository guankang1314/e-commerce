package com.imooc.ecommerce.service.communucation;

import feign.Feign;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author qingtian
 * @version 1.0
 * @description: okHttp配置
 * @date 2022/3/20 17:56
 */
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class) //在feign配置前该配置就要生效
public class FeignHttpConfig {

    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)    //设置连接超时
                .readTimeout(5,TimeUnit.SECONDS)    //设置读超时
                .writeTimeout(5,TimeUnit.SECONDS)   //设置写超时
                .retryOnConnectionFailure(true)     //是否自动重连
                // 配置连接池中的最大空闲线程个数为 10，并保持5分钟
                .connectionPool(new ConnectionPool(10,5L,TimeUnit.MINUTES))
                .build();
    }
}
