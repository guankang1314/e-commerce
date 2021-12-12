package com.imooc.ecommerce.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qingtian
 * @description:非对称加密算法生成公钥和私钥
 * @Package com.imooc.ecommerce.service
 * @date 2021/12/5 10:31
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RSATest {

    @Test
    public void generateKeyBytes() throws Exception {

        KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
        rsa.initialize(2048);

        //生成公钥和私钥对
        KeyPair keyPair = rsa.generateKeyPair();
        //获取公钥私钥对
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        log.info("privateKey : [{}]", Base64.encode(privateKey.getEncoded()));
        log.info("publicKey : [{}]",Base64.encode(publicKey.getEncoded()));
    }
}
