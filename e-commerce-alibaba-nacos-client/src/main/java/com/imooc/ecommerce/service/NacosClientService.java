package com.imooc.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
