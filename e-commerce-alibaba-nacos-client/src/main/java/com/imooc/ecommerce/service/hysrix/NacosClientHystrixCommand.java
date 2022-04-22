package com.imooc.ecommerce.service.hysrix;

import com.imooc.ecommerce.service.NacosClientService;
import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;

import java.util.Collections;
import java.util.List;

/**
 * @author qingtian
 * @version 1.0
 * @description: 给 nacosClientService 实现包装
 * Hystrix 舱壁模式
 * 1. 线程池
 * 2. 信号量
 * @date 2022/4/6 22:30
 */
@Slf4j
public class NacosClientHystrixCommand extends HystrixCommand<List<ServiceInstance>> {

    /**
     * 需要保护的服务
     */
    private final NacosClientService nacosClientService;


    /**
     * 方法需要传递的参数
     */
    private final String serviceId;

    public NacosClientHystrixCommand(NacosClientService nacosClientService, String serviceId) {

        super(
                Setter.withGroupKey(
                        HystrixCommandGroupKey.Factory.asKey("NacosClientService"))
                        .andCommandKey(HystrixCommandKey.Factory.asKey("NacosClientServiceCommand"))
                        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("NacosClientPool"))
                        //线程池 key 配置
                        .andCommandPropertiesDefaults(
                                HystrixCommandProperties.Setter()
                                        //线程池隔离策略
                                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                                        //开启降级
                                        .withFallbackEnabled(true)
                                        //开启熔断器
                                        .withCircuitBreakerEnabled(true)
                        )
        );

        //可以配置信号量隔离策略
//        Setter setter = Setter.withGroupKey(
//                        HystrixCommandGroupKey.Factory.asKey("NacosClientService"))
//                .andCommandKey(HystrixCommandKey.Factory.asKey("NacosClientServiceCommand"))
//                .andCommandPropertiesDefaults(
//                        HystrixCommandProperties.Setter()
//                                //至少有10个请求熔断器才开始计算成功率
//                                .withCircuitBreakerRequestVolumeThreshold(10)
//                                //熔断器在启用5秒后会进入半开启模式 尝试进行请求
//                                .withCircuitBreakerSleepWindowInMilliseconds(5000)
//                                //当请求错误率达到 50% 时开启熔断器
//                                .withCircuitBreakerErrorThresholdPercentage(50)
//                                //使用信号量熔断配置
//                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
//                );

        this.nacosClientService = nacosClientService;
        this.serviceId = serviceId;
    }

    /**
     * 要保护的方法调用写在 run 方法中
     * @return
     * @throws Exception
     */
    @Override
    protected List<ServiceInstance> run() throws Exception {
        log.info("NacosClientService In Hystrix Command to Get Service Instance : [{}]. [{}]",
                this.serviceId,Thread.currentThread().getName());
        return this.nacosClientService.getNacosClientInfo(this.serviceId);
    }

    /**
     * 降级处理策略
     * @return
     */
    @Override
    protected List<ServiceInstance> getFallback() {
        log.warn("NacoClientService run error : [{}], [{}]", this.serviceId, Thread.currentThread().getName());
        return Collections.emptyList();
    }
}
