package com.imooc.ecommerce.service.communucation;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.constant.CommonConstant;
import com.imooc.ecommerce.vo.JwtToken;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author qingtian
 * @version 1.0
 * @description: 使用restTemplate实现微服务通信
 * @date 2022/3/10 22:56
 */
@Slf4j
@Service
public class UseRestTemplateService {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     *
     * @param usernameAndPassword
     * @return
     */
    public JwtToken getTokenFromAuthorityService(UsernameAndPassword usernameAndPassword) {
        String requestUrl = "http://127.0.0.1:7000/ecommerce-authority-center/authority/token";
        log.info("RestTemplate request url and body : [{}],[{}]", requestUrl, JSON.toJSONString(usernameAndPassword));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new RestTemplate().postForObject(
                requestUrl,
                new HttpEntity<>(JSON.toJSONString(usernameAndPassword), headers),
                JwtToken.class
        );
    }

    /**
     * 通过注册中心拿到服务的信息（是所有的实例），再发起调用
      * @param usernameAndPassword
     * @return
     */
    public JwtToken getTokenFromAuthorityServiceWithLoadBalancer(UsernameAndPassword usernameAndPassword){

        ServiceInstance serviceInstance = loadBalancerClient.choose(CommonConstant.AUTHORITY_CENTER_SERVICE_ID);
        log.info("Nacos Client Info : [{}],[{}],[{}]",
                serviceInstance.getServiceId(),serviceInstance.getInstanceId(),
                JSON.toJSONString(serviceInstance.getMetadata()));
        String requestUrl = String.format(
                "http://%s:%s/ecommerce-authority-center/authority/token",
                serviceInstance.getHost(),
                serviceInstance.getPort()
        );
        log.info("RestTemplate request url and body : [{}],[{}]",requestUrl, JSON.toJSONString(usernameAndPassword));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new RestTemplate().postForObject(
                requestUrl,
                new HttpEntity<>(JSON.toJSONString(usernameAndPassword),headers),
                JwtToken.class
        );

    }
}
