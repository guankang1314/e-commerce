package com.imooc.ecommerce.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Guank
 * @version 1.0
 * @description: 缓存 request 的 Body 全局过滤器 必须第一个执行
 * @date 2022-08-12 15:39
 */
@Component
@Slf4j
@ServletComponentScan
public class RequestCachedBodyFilter implements Filter, Ordered {

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 1;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest request = null;
        if (servletRequest instanceof HttpServletRequest) {
            request = new RequestWrapper((HttpServletRequest) servletRequest);
        }
        if (request != null) {
            filterChain.doFilter(request, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
