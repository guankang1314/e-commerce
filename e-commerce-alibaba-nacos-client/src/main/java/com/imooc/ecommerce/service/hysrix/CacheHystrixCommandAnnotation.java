package com.imooc.ecommerce.service.hysrix;

import com.imooc.ecommerce.service.NacosClientService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qingtian
 * @version 1.0
 * @description: 使用注解方式开启 Hystrix 请求缓存
 * @date 2022/4/23 10:20
 */
@Service
@Slf4j
public class CacheHystrixCommandAnnotation {

    private final NacosClientService nacosClientService;

    public CacheHystrixCommandAnnotation(NacosClientService nacosClientService) {
        this.nacosClientService = nacosClientService;
    }



    /**
     *  第一种使用方法
     * @return
     */
    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(commandKey = "CacheHystrixCommandAnnotation")
    public List<ServiceInstance> useCacheByAnnotation01(String serviceId) {
        log.info("use cache01 to get nacos client info : [{}]", serviceId);
        return nacosClientService.getNacosClientInfo(serviceId);
    }

    //必须和上面的 commandKey 一致
    @CacheRemove(commandKey = "CacheHystrixCommandAnnotation",
                cacheKeyMethod = "getCacheKey")
    @HystrixCommand
    public void flushCacheByAnnotation01(String cacheId) {
        //实际上这个方法里不需要做任何操作
        log.info("flush hystrix cache key : [{}]",cacheId);
    }

    public String getCacheKey(String cacheId) {
        return cacheId;
    }

    /**
     * 第二种使用方式
     * @param serviceId
     * @return
     */
    @CacheResult
    @HystrixCommand(commandKey = "CacheHystrixCommandAnnotation")
    public List<ServiceInstance> useCacheByAnnotation02(@CacheKey String serviceId) {
        log.info("use cache02 to get nacos client info : [{}]", serviceId);
        return nacosClientService.getNacosClientInfo(serviceId);
    }

    /**
     * 清除缓存
     * 必须和上面的 commandKey 一致
     * @param cacheId
     */
    @CacheRemove(commandKey = "CacheHystrixCommandAnnotation")
    @HystrixCommand
    public void flushCacheByAnnotation02(@CacheKey String cacheId) {
        //实际上这个方法里不需要做任何操作
        log.info("flush hystrix cache key : [{}]",cacheId);
    }

    /**
     * 第三种使用方式
     * @param serviceId
     * @return
     */
    @CacheResult
    @HystrixCommand(commandKey = "CacheHystrixCommandAnnotation")
    public List<ServiceInstance> useCacheByAnnotation03(String serviceId) {
        log.info("use cache02 to get nacos client info : [{}]", serviceId);
        return nacosClientService.getNacosClientInfo(serviceId);
    }

    /**
     * 清除缓存
     * 必须和上面的 commandKey 一致
     * @param cacheId
     */
    @CacheRemove(commandKey = "CacheHystrixCommandAnnotation")
    @HystrixCommand
    public void flushCacheByAnnotation03(String cacheId) {
        //实际上这个方法里不需要做任何操作
        log.info("flush hystrix cache key : [{}]",cacheId);
    }

}
