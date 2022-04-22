package com.imooc.ecommerce.service.hysrix;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.service.NacosClientService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import rx.Observable;
import rx.Subscriber;

import java.util.Collections;
import java.util.List;

/**
 * @author qingtian
 * @version 1.0
 * @description: HystrixCommand, 隔离策略是基于信号量实现的
 * @date 2022/4/12 23:16
 */
@Slf4j
public class NacosClientHystrixObservableCommand extends HystrixObservableCommand<List<ServiceInstance>> {

    /**
     * 需要保护的服务
     */
    private final NacosClientService nacosClientService;

    /**
     * 方法需要传递的参数
     */
    private final List<String> serviceIds;

    public NacosClientHystrixObservableCommand(NacosClientService nacosClientService, List<String> serviceIds) {
        super(
                HystrixObservableCommand.Setter
                        .withGroupKey(HystrixCommandGroupKey
                                .Factory.asKey("NacosClientService"))
                        .andCommandKey(HystrixCommandKey
                                .Factory.asKey("NacosClientHystrixObservableCommand"))
                        .andCommandPropertiesDefaults(
                                HystrixCommandProperties.Setter()
                                        //开启降级
                                        .withFallbackEnabled(true)
                                        //开启熔断器
                                        .withCircuitBreakerEnabled(true)
                        )

        );
        this.nacosClientService = nacosClientService;
        this.serviceIds = serviceIds;
    }

    /**
     * 需要保护的方法
     * @return
     */
    @Override
    protected Observable<List<ServiceInstance>> construct() {
        return Observable.create(new Observable.OnSubscribe<List<ServiceInstance>>() {
            //Observable 有三个关键的事件方法，分别是 onNext:方法调用, onCompleted:方法执行完成, onError:方法执行出错
            @Override
            public void call(Subscriber<? super List<ServiceInstance>> subscriber) {
                try {
                    //第一次完成时进入，打印出信息
                    if (!subscriber.isUnsubscribed()) {
                        log.info("subscriber command task : [{}],[{}]",
                                JSON.toJSONString(serviceIds),Thread.currentThread().getName());
                        serviceIds.forEach(
                                s -> subscriber.onNext(nacosClientService.getNacosClientInfo(s))
                        );
                        subscriber.onCompleted();
                        log.info("command task completed: [{}], [{}]",
                                JSON.toJSONString(serviceIds), Thread.currentThread().getName());
                    }
                }catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
        });
    }

    /**
     * 降级策略
     * @return
     */
    @Override
    protected Observable<List<ServiceInstance>> resumeWithFallback() {
        return Observable.create(new Observable.OnSubscribe<List<ServiceInstance>>() {
            @Override
            public void call(Subscriber<? super List<ServiceInstance>> subscriber) {
                try {
                    //第一次完成时进入，打印出信息
                    if (!subscriber.isUnsubscribed()) {
                        log.info("fallback ---- subscriber command task : [{}],[{}]",
                                JSON.toJSONString(serviceIds),Thread.currentThread().getName());
                        subscriber.onNext(Collections.emptyList());
                        subscriber.onCompleted();
                        log.info("command task completed: [{}], [{}]",
                                JSON.toJSONString(serviceIds), Thread.currentThread().getName());
                    }
                }catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
        });
    }
}
