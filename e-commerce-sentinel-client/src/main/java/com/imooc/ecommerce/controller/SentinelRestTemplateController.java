package com.imooc.ecommerce.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.vo.JwtToken;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author Guank
 * @version 1.0
 * @description: 使用 sentinel 保护 resttemplate 服务间调用
 * @date 2022-07-24 16:31
 */
@Slf4j
@RestController
public class SentinelRestTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 从授权服务器中 获取 jwtToken
     * 1. 流控降级: 针对于簇点链路的 http://127.0.0.1:7000/ecommerce-authority-center/authority/token
     * 2. 容错降级: 对于服务不可用时不能生效
     * @param usernameAndPassword
     * @return
     */
    @PostMapping("/get-token")
    public JwtToken getJwtTokenFromAuthorityCenter(@RequestBody UsernameAndPassword usernameAndPassword) {
        String requestUrl = "http://127.0.0.1:7000/ecommerce-authority-center/authority/token";
        log.info("RestTemplate from authority center url and body : [{}] , [{}]"
                ,requestUrl, JSON.toJSONString(usernameAndPassword));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.postForObject(requestUrl,
                new HttpEntity<>(JSON.toJSONString(usernameAndPassword),httpHeaders),
                JwtToken.class);
    }

}
