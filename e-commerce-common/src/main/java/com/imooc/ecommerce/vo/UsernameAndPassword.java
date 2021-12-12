package com.imooc.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.ecommerce.vo
 * @date 2021/12/5 11:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsernameAndPassword {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
