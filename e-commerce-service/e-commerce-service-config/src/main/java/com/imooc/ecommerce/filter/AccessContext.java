package com.imooc.ecommerce.filter;

import com.imooc.ecommerce.vo.LoginUserInfo;

/**
 * @author qingtian
 * @version 1.0
 * @description: 使用ThreadLocal 去单独储存每一个线程携带的LoginUserInfo信息
 * @date 2021/12/27 20:15
 */
public class AccessContext {

    private static final ThreadLocal<LoginUserInfo> loginUserInfo = new ThreadLocal<>();

    public static LoginUserInfo getLoginUserInfo() {
        return loginUserInfo.get();
    }

    public static void setLoginUserInfo(LoginUserInfo userInfo) {
        loginUserInfo.set(userInfo);
    }

    public static void clearLoginUserInfo() {
        loginUserInfo.remove();
    }
}
