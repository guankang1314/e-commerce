package com.imooc.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.annotation.IgnoreResponseAdvice;
import com.imooc.ecommerce.service.IJWTService;
import com.imooc.ecommerce.vo.JwtToken;
import com.imooc.ecommerce.vo.UsernameAndPassword;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.ecommerce.controller
 * @date 2021/12/5 19:52
 */
@Slf4j
@RestController
@RequestMapping("/authority")
public class AuthorityController {


    @Autowired
    private IJWTService ijwtService;

    /**
     * 获取token, 登录功能
     * @param usernameAndPassword
     * @return
     * @throws Exception
     */
    @IgnoreResponseAdvice
    @PostMapping("/token")
    public JwtToken token(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {

        log.info("request to get token with params : [{}]",
                JSON.toJSONString(usernameAndPassword));
        return new JwtToken(ijwtService.generateToken(
                usernameAndPassword.getUsername(),
                usernameAndPassword.getPassword()
        ));
    }

    /**
     * 注册用户并返回当前注册用户的token，通过授权中心创建用户
     * @param usernameAndPassword
     * @return
     * @throws Exception
     */
    @IgnoreResponseAdvice
    @PostMapping("/register")
    public JwtToken register(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {

        log.info("register user with params : [{}]",JSON.toJSONString(usernameAndPassword));

        return new JwtToken(ijwtService.registerUserAndGenerateToken(usernameAndPassword));
    }
}
