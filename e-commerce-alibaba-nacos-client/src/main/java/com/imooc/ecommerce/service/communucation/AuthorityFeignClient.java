package com.imooc.ecommerce.service.communucation;

import com.imooc.ecommerce.service.communucation.hystrix.AuthorityFeignClientFallbackFactory;
import com.imooc.ecommerce.vo.JwtToken;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author qingtian
 * @version 1.0
 * @description: TODO
 * @date 2022/3/17 22:48
 */
@FeignClient(contextId = "AuthorityFeignClient", value = "e-commerce-authority-center",
//        fallback = AuthorityFeignClientFallback.class
        fallbackFactory = AuthorityFeignClientFallbackFactory.class
)
public interface AuthorityFeignClient {

    @RequestMapping(value = "/ecommerce-authority-center/authority/token",
    method = RequestMethod.POST,consumes = "application/json",produces = "application/json")
    JwtToken getTokenByFeign(@RequestBody UsernameAndPassword usernameAndPassword);
}
