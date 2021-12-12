package com.imooc.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qingtian
 * @description:登录用户信息
 * @Package com.imooc.ecommerce.vo
 * @date 2021/12/5 11:22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserInfo {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;
}
