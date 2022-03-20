package com.imooc.ecommerce.service.communucation;

import com.imooc.ecommerce.vo.JwtToken;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Random;

/**
 * @author qingtian
 * @version 1.0
 * @description: 使用feign的原生api
 * @date 2022/3/20 18:08
 */
@Slf4j
@Service
public class UseFeignApi {

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 使用feign的原生api调用远端服务
     * Feign 默认配置初始化，设置自定义配置，生成代理对象
     * @return
     */
    public JwtToken thinkingInFeign(UsernameAndPassword usernameAndPassword) {

        //通过反射拿到 serviceId
        String serviceId = null;
        Annotation[] annotations = AuthorityFeignClient.class.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(FeignClient.class)) {
                serviceId = ((FeignClient)annotation).value();
                log.info("get service Id from AuthorityFeignClient : [{}]",serviceId);
                break;
            }
        }

        //如果serviceId不存在，抛出异常
        if (null == serviceId) {
            throw new RuntimeException("can not get serviceId");
        }

        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (CollectionUtils.isEmpty(instances)) {
            throw new RuntimeException("can not get target instance from service id" + serviceId);
        }

        //随机选择一个服务实例，模拟负载均衡
        ServiceInstance randomInstance = instances.get(new Random().nextInt(instances.size()));
        log.info("choose service from instance : [{}],[{}],[{}]",serviceId,randomInstance.getHost(),randomInstance.getPort());

        AuthorityFeignClient feignClient = Feign.builder()      //feign 默认初始化配置
                .encoder(new GsonEncoder())                     //设置自定义配置
                .decoder(new GsonDecoder())                     //设置自定义配置
                .logLevel(Logger.Level.FULL)                    //设置自定义配置
                .target(AuthorityFeignClient.class,
                        String.format("http://%s:%s",
                                randomInstance.getHost(),randomInstance.getPort())      //生成feign的代理客户端
                );
        return feignClient.getTokenByFeign(usernameAndPassword);
    }
}
