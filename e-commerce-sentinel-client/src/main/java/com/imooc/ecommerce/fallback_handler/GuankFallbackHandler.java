package com.imooc.ecommerce.fallback_handler;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.vo.JwtToken;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Guank
 * @version 1.0
 * @description: sentinel 回退降级的兜底策略
 * @date 2022-07-24 17:43
 */
@Slf4j
public class GuankFallbackHandler {

    /**
     * getJwtTokenFromAuthorityCenter 的外部调用兜底策略
     * @param usernameAndPassword
     * @return
     */
    public static JwtToken getTokenFromAuthorityServiceFallback(UsernameAndPassword usernameAndPassword) {
        log.error("get token from authority service fallback : [{}]", JSON.toJSONString(usernameAndPassword));
        return new JwtToken("imooc-guank-fallback");
    }

    public static JwtToken ignoreExceptionFallback(Integer code) {
        log.error("ignore exception input code is : [{}] has trigger exception",code);
        return new JwtToken("imooc-guan");
    }
}
