package com.imooc.ecommerce.service.communucation.hystrix;

import com.imooc.ecommerce.service.communucation.AuthorityFeignClient;
import com.imooc.ecommerce.vo.JwtToken;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author qingtian
 * @version 1.0
 * @description: AuthorityFeignClient后备FallbackFactory
 * @date 2022/4/26 23:03
 */
@Slf4j
@Component
public class AuthorityFeignClientFallbackFactory implements FallbackFactory<AuthorityFeignClient> {

    /**
     *
     * @param throwable feign调用是抛出的异常
     * @return
     */
    @Override
    public AuthorityFeignClient create(Throwable throwable) {
        log.warn("authority feign client get token by feign request error " +
                "(Hystrix FallbackFactory) : [{}]", throwable.getMessage(), throwable);
        return new AuthorityFeignClient() {
            @Override
            public JwtToken getTokenByFeign(UsernameAndPassword usernameAndPassword) {
                return new JwtToken("guank-factory");
            }
        };
    }
}
