package com.imooc.ecommerce.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
