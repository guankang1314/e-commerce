package com.imooc.ecommerce.controller;

import com.imooc.ecommerce.annotation.IgnoreResponseAdvice;
import com.imooc.ecommerce.vo.CommonResponse;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Guank
 * @version 1.0
 * @description: 测试缓存 request 中的 body
 * @date 2022-08-11 23:47
 */
@RestController
@RequestMapping("/cache")
@Slf4j
public class RequestCacheController {

    @PostMapping("/requestCache")
    public String requestCache(@RequestBody UsernameAndPassword usernameAndPassword) {
        return usernameAndPassword.getPassword();
    }
}
