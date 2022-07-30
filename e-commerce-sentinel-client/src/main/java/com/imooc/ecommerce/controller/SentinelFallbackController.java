package com.imooc.ecommerce.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.fallback_handler.GuankFallbackHandler;
import com.imooc.ecommerce.vo.JwtToken;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.http11.upgrade.UpgradeServletOutputStream;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author Guank
 * @version 1.0
 * @description: Sentinel 对服务容错降级的功能
 * @date 2022-07-24 17:33
 */
@Slf4j
@RestController
@RequestMapping("/sentinel-fallback")
public class SentinelFallbackController {

    /**
     * 注入没有被 sentinel 增强的 restTemplate
     */
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/get-token")
    @SentinelResource(value = "getJwtTokenFromAuthorityCenter",
            fallback = "getTokenFromAuthorityServiceFallback", fallbackClass = GuankFallbackHandler.class)
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

    /**
     * 让 Sentinel 忽略一些异常
     * @param code
     * @return
     */
    @GetMapping("/ignore-exception")
    @SentinelResource(value = "ignoreException",
                    fallback = "ignoreExceptionFallback",
                    fallbackClass = GuankFallbackHandler.class,
                    exceptionsToIgnore = {NullPointerException.class})
    public JwtToken ignoreException(@RequestParam Integer code) {
        if (code % 2 == 0) {
            throw new NullPointerException("you input code is : " + code);
        }
        return new JwtToken("imooc-guank-fallback");
    }

}
