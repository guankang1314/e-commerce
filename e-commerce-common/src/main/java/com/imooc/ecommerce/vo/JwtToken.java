package com.imooc.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qingtian
 * @description:授权中心鉴权之后给客户端的token
 * @Package com.imooc.ecommerce.vo
 * @date 2021/12/5 11:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {

    /**
     * Jwt
     */
    private String token;
}
