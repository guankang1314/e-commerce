package com.imooc.ecommerce.constant;

/**
 * @author qingtian
 * @version 1.0
 * @description: 定义网关常量
 * @date 2021/12/14 15:15
 */
public class GatewayConstant {

    /**
     * @description: 登录uri
     * @date 2021/12/14 15:20
     */
    public static final String LOGIN_URI = "/e-commerce/login";

    /**
     * @description: 注册uri
     * @date 2021/12/14 18:52
     */
    public static final String REGISTER_URI = "/e-commerce/register";

    /**
     * @description: 去授权中心拿到token的格式化接口
     * @date 2021/12/14 18:58
     */
    public static final String AUTHORITY_CENTER_TOKEN_URL_FORMAT =
            "http://%s/%s/ecommerce-authority-center/authority/token";

    /**
     * @description: 去授权中心注册并拿到token的rurl格式化接口
     * @date 2021/12/14 21:57
     */
    public static final String AUTHORITY_CENTER_REGISTER_URL_FORMAT =
            "http://%s/%s/ecommerce-authority-center/authority/register";
}
