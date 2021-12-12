package com.imooc.ecommerce.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.dao.EcommerceUserDao;
import com.imooc.ecommerce.entity.EcommerceUser;

import cn.hutool.crypto.digest.MD5;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.ecommerce.service
 * @date 2021/12/5 10:21
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class EcommerceUserTest {

    @Autowired
    private EcommerceUserDao ecommerceUserDao;


    @Test
    public void createUserRecord() {

        EcommerceUser user = new EcommerceUser();
        user.setUsername("imoocguankang@163.com");
        user.setPassword(MD5.create().digestHex("12345678"));
        user.setExtraInfo("{}");
        log.info("save user : [{}]",
                JSON.toJSON(ecommerceUserDao.save(user)));
    }
}
