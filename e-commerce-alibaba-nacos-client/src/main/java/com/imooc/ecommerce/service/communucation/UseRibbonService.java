package com.imooc.ecommerce.service.communucation;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.constant.CommonConstant;
import com.imooc.ecommerce.vo.JwtToken;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import com.netflix.loadbalancer.*;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qingtian
 * @version 1.0
 * @description: 使用Ribbon实现微服务通信
 * @date 2022/3/16 23:01
 */
@Slf4j
@Service
public class UseRibbonService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 通过 Ribbon 调用 Authority 服务获取 Token
     * @param usernameAndPassword
     * @return
     */
    public JwtToken getTokenFromAuthorityServiceByRibbon(UsernameAndPassword usernameAndPassword) {

        String requestUrl = String.format(
                "http://%s/ecommerce-authority-center/authority/token",
                CommonConstant.AUTHORITY_CENTER_SERVICE_ID
        );
        log.info("login request url and body : [{}], [{}]",requestUrl, JSON.toJSONString(usernameAndPassword));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.postForObject(
                requestUrl,
                new HttpEntity<>(JSON.toJSONString(usernameAndPassword),headers),
                JwtToken.class
        );
    }

    /**
     * 使用ribbon 原生api操作
     * @param usernameAndPassword
     * @return
     */
    public JwtToken thinkingInRibbon(UsernameAndPassword usernameAndPassword) {

        String urlFormat = "http://%s/ecommerce-authority-center/authority/token";

        //1. 找到服务提供方的地址和端口号
        List<ServiceInstance> instances = discoveryClient.getInstances(CommonConstant.AUTHORITY_CENTER_SERVICE_ID);

        //构造服务列表
        List<Server> servers = new ArrayList<>(instances.size());
        instances.forEach(i -> {
            servers.add(new Server(i.getHost(),i.getPort()));
            log.info("found target instance : [{}] -> [{}]",i.getHost(),i.getPort());
        });

        //2. 使用负载均衡策略实现远端服务调用
        //构建 Ribbon 负载实例
        BaseLoadBalancer loadBalancer = LoadBalancerBuilder.newBuilder()
                .buildFixedServerListLoadBalancer(servers);

        //3. 设置负载均衡策略
        loadBalancer.setRule(new RetryRule(new RandomRule(),300));

        String result = LoadBalancerCommand.builder().withLoadBalancer(loadBalancer)
                .build().submit(server -> {

                    String targetUrl = String.format(
                            urlFormat,
                            String.format("%s:%s",server.getHost(),server.getPort())
                    );
                    log.info("target requestUrl is : [{}]",targetUrl);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);

                    String tokenStr = new RestTemplate().postForObject(
                            targetUrl,
                            new HttpEntity<>(JSON.toJSONString(usernameAndPassword),headers),
                            String.class
                    );
                    return Observable.just(tokenStr);
                    //获取到请求的第一个结果的时候返回回来
                }).toBlocking().first().toString();
        return JSON.parseObject(result,JwtToken.class);
    }
}
