package com.imooc.ecommerce.util;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;


import cn.hutool.core.codec.Base64Decoder;
import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.constant.CommonConstant;
import com.imooc.ecommerce.vo.LoginUserInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;


/**
 * @author qingtian
 * @description:JWTToken解密工具类
 * @Package com.imooc.ecommerce.util
 * @date 2021/12/5 20:26
 */
public class TokenParseUtil {

    /**
     * 根据本地储存的公钥获取PublicKey对象
     * @return
     */
    private static PublicKey getPublicKey() throws Exception {

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                Base64.getDecoder().decode(CommonConstant.PUBLIC_KEY)
        );
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

    /**
     * 从JWT Token中解析出LoginUserInfo
     * @param token
     * @return
     * @throws Exception
     */
    public static LoginUserInfo parseUserInfoFromToken(String token) throws Exception {

        if (null == token) {
            return null;
        }

        Jws<Claims> claimsJws = parseToken(token,getPublicKey());
        Claims body = claimsJws.getBody();

        //如果body已经过期
        if (body.getExpiration().before(Calendar.getInstance().getTime())) {
            return null;
        }

        //返回token中保存的用户信息
        return JSON.parseObject(
                body.get(CommonConstant.JWT_USER_INFO_KEY).toString(),
                LoginUserInfo.class
        );
    }

    /**
     * 通过公钥解析JWT Token
     * @param token
     * @param publicKey
     * @return
     */
    private static Jws<Claims> parseToken(String token,PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }
}
