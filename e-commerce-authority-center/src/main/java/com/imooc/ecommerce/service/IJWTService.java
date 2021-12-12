package com.imooc.ecommerce.service;

import org.springframework.stereotype.Service;

import com.imooc.ecommerce.vo.UsernameAndPassword;

/**
 * @author qingtian
 * @description:JWT相关服务接口定义
 * @Package com.imooc.ecommerce.service
 * @date 2021/12/5 17:28
 */
public interface IJWTService {

    /**
     * 生成JWT Token，使用默认的超时时间
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    String generateToken(String username,String password) throws Exception;

    /**
     * 生成JWT Token,过期时间单位是天
     * @param username
     * @param password
     * @param expire
     * @return
     * @throws Exception
     */
    String generateToken(String username,String password,int expire) throws Exception;

    /**
     * 注册用户并生成token返回
     * @param usernameAndPassword
     * @return
     * @throws Exception
     */
    String registerUserAndGenerateToken(UsernameAndPassword  usernameAndPassword) throws Exception;

}
