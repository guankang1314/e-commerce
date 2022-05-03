package com.imooc.ecommerce.service.communucation.hystrix;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.service.communucation.AuthorityFeignClient;
import com.imooc.ecommerce.vo.JwtToken;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author qingtian
 * @version 1.0
 * @description: AuthorityFeignClient后备fallback
 * @date 2022/4/26 22:55
 */
@Slf4j
@Component
public class AuthorityFeignClientFallback implements AuthorityFeignClient {

    @Override
    public JwtToken getTokenByFeign(UsernameAndPassword usernameAndPassword) {
        log.info("authority feign client get token by feign request error" +
                "(Hystrix Fallback) : [{}]", JSON.toJSONString(usernameAndPassword));
        return new JwtToken("guank");
    }
}
