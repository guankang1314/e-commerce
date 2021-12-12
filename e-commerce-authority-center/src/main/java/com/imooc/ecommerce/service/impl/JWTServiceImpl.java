package com.imooc.ecommerce.service.impl;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.constant.AuthorityConstant;
import com.imooc.ecommerce.constant.CommonConstant;
import com.imooc.ecommerce.dao.EcommerceUserDao;
import com.imooc.ecommerce.entity.EcommerceUser;
import com.imooc.ecommerce.service.IJWTService;
import com.imooc.ecommerce.vo.LoginUserInfo;
import com.imooc.ecommerce.vo.UsernameAndPassword;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.date.DateUnit;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

/**
 * @author qingtian
 * @description:JWT接口实现类
 * @Package com.imooc.ecommerce.service.impl
 * @date 2021/12/5 17:34
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class JWTServiceImpl implements IJWTService {


    @Autowired
    private EcommerceUserDao ecommerceUserDao;

    @Override
    public String generateToken(String username, String password) throws Exception {
        return generateToken(username,password,0);
    }

    @Override
    public String generateToken(String username, String password, int expire) throws Exception {

        EcommerceUser user = ecommerceUserDao.findByUsernameAndPassword(username, password);
        if (null == user) {
            log.error("can not find user :[{}],[{}]",username,password);
            return null;
        }

        LoginUserInfo userInfo = new LoginUserInfo(user.getId(), user.getUsername());
        if (expire <= 0) {
            expire = AuthorityConstant.DEFAULT_EXPIRE_DAY;
        }

        //计算超时时间
        ZonedDateTime zdt = LocalDate.now().plus(expire, ChronoUnit.DAYS)
                .atStartOfDay(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());

        return Jwts.builder()
                .claim(CommonConstant.JWT_USER_INFO_KEY, JSON.toJSONString(userInfo))
                .setId(UUID.randomUUID().toString())
                .setExpiration(expireDate)
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception {

        //先检验用户名是否存在
        EcommerceUser oldUser = ecommerceUserDao.findByUsername(usernameAndPassword.getUsername());
        if (null != oldUser) {
            log.error("username is registered : [{}]",oldUser.getUsername());
            return null;
        }

        EcommerceUser ecommerceUser = new EcommerceUser();
        ecommerceUser.setUsername(usernameAndPassword.getUsername());
        ecommerceUser.setPassword(usernameAndPassword.getPassword());
        ecommerceUser.setExtraInfo("{}");

        //注册新用户
        ecommerceUser = ecommerceUserDao.save(ecommerceUser);
        log.info("register user success : [{}], [{}]",ecommerceUser.getUsername(),ecommerceUser.getId());

        //生成token并返回
        return generateToken(ecommerceUser.getUsername(),ecommerceUser.getPassword());
    }

    /**
     * 根据本地存储的私钥获取到PrivateKey对象
     * @return
     * @throws Exception
     */
    private PrivateKey getPrivateKey() throws Exception {

        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                                                new BASE64Decoder().decodeBuffer(AuthorityConstant.PRIVATE_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(priPKCS8);
    }
}
