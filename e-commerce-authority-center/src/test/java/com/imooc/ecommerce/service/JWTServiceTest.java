package com.imooc.ecommerce.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.util.TokenParseUtil;
import com.imooc.ecommerce.vo.LoginUserInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.ecommerce.service
 * @date 2021/12/5 21:19
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class JWTServiceTest {


    @Autowired
    private IJWTService ijwtService;

    @Test
    public void testGenerateAndParseToken() throws Exception {

        String jwtToken = ijwtService.generateToken(
                "imoocguankang@163.com",
                "25d55ad283aa400af464c76d713c07ad"
        );
        log.info("jwt token is : [{}]",jwtToken);
        LoginUserInfo userInfo = TokenParseUtil.parseUserInfoFromToken(jwtToken);
        log.info("parse token : [{}]", JSON.toJSONString(userInfo));
    }


}
