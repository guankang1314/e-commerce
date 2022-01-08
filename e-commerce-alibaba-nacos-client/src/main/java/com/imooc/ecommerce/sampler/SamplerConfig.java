package com.imooc.ecommerce.sampler;

import brave.sampler.RateLimitingSampler;
import brave.sampler.Sampler;
import org.springframework.cloud.sleuth.sampler.ProbabilityBasedSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qingtian
 * @version 1.0
 * @description: 使用配置的方式设置抽样率
 * @date 2022/1/1 20:53-
 */
@Configuration
public class SamplerConfig {

    /**
     * @description:  限速采集
     * @param:
     * @return: brave.sampler.Sampler
     * @date: 2022/1/1 20:55
     */
    @Bean
    public Sampler sampler() {
        return RateLimitingSampler.create(100);
    }

//    /**
//     * @description:  概率采集，这两个中间使用一个就行，一般使用限速采集
//     * @param:
//     * @return: brave.sampler.Sampler
//     * @date: 2022/1/1 20:56
//     */
//    @Bean
//    public Sampler defaultSampler() {
//        return ProbabilityBasedSampler.create(0.5f);
//    }
}
