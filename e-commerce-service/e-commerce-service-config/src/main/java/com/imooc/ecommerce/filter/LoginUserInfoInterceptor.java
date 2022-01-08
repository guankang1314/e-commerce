package com.imooc.ecommerce.filter;

import com.imooc.ecommerce.constant.CommonConstant;
import com.imooc.ecommerce.util.TokenParseUtil;
import com.imooc.ecommerce.vo.LoginUserInfo;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qingtian
 * @version 1.0
 * @description: 用户身份统一登录拦截
 * @date 2021/12/27 20:27
 */
@Slf4j
@Component
public class LoginUserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //部分请求不需要提供身份信息，白名单
        if (checkWhiteListUrl(request.getRequestURI())) {
            return true;
        }
        //先尝试从http header中拿到token
        String token = request.getHeader(CommonConstant.JWT_USER_INFO_KEY);

        LoginUserInfo loginUserInfo = null;
        try {
            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        }catch (Exception ex) {
            log.error("parse login user info error : [{}]",ex.getMessage(),ex);
        }

        if (null == loginUserInfo) {
            throw new RuntimeException("can not parse current login user");
        }

        log.info("set login user info : [{}]",request.getRequestURI());
        //设置当前请求将loginUserInfo 放入到AccessContext中
        AccessContext.setLoginUserInfo(loginUserInfo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    //在请求完全结束后开始调用此方法，常用于清理资源等工作
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if (null != AccessContext.getLoginUserInfo()) {
            AccessContext.clearLoginUserInfo();
        }
    }

    /**
     * @description: 校验url是否在白名单里，比如说swagger接口
     * @param: url
     * @return: boolean
     * @date: 2022/1/8 18:53
     */
    private boolean checkWhiteListUrl(String url) {
        return StringUtils.containsAny(url,
                "springfox","swagger","v2","webjars","doc.html");
    }
}
