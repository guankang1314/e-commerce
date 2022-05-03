package com.imooc.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.ecommerce.service
 * @date 2021/12/4 11:10
 */
@Slf4j
@Service
public class NacosClientService {

    private final DiscoveryClient discoveryClient;

    public NacosClientService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public List<ServiceInstance> getNacosClientInfo(String serviceId) {

//        throw new RuntimeException("has some error");
        log.info("request nacos client to get service instance info:[{}]",serviceId);
        return discoveryClient.getInstances(serviceId);
    }


    /**
     * 提供给请求合并(编程方式的 hystrix 请求合并)
     * @param serviceIds
     * @return
     */
    public List<List<ServiceInstance>> getNacosInfos(List<String> serviceIds) {
        log.info("request nacos client to get service instance infos : [{}]", JSON.toJSONString(serviceIds));
        List<List<ServiceInstance>> result = new ArrayList<>(serviceIds.size());
        serviceIds.forEach(s -> result.add(discoveryClient.getInstances(s)));
        return result;
    }

    /**
     * 使用注解的方式实现 Hystrix 请求合并
     * @param serviceId
     * @return
     */
    @HystrixCollapser(
            batchMethod = "findNacosClientInfos",
            //全局性的请求合并策略
            scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
            //合并时间区间属性
            collapserProperties = {
                    @HystrixProperty(name = "timerDelayInMilliseconds", value = "300")
            }
    )
    public Future<List<ServiceInstance>> findNacosClientInfo(String serviceId) {
        //系统运行正常，不会走这个方法，一般直接抛出异常
        throw new RuntimeException("this method body should not be executed!");
    }

    @HystrixCommand
    public List<List<ServiceInstance>> findNacosClientInfos(List<String> serviceIds) {
        log.info("coming in find nacos client infos : [{}]",JSON.toJSONString(serviceIds));
        return getNacosInfos(serviceIds);
    }
}
