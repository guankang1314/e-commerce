package com.imooc.ecommerce.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private static final List<String> WHITE_LIST = new ArrayList<>();

    //设置 filter 白名单
    static {

    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest request = null;
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            String contentType = req.getContentType();
            String method = "multipart/form-data";
            if (isFiltered(req.getServletPath())) {
                if (null != contentType && contentType.contains(method)) {
                    // 将转化后的 request 放入过滤链中
                    req = new StandardServletMultipartResolver().resolveMultipart(req);
                }
                request = new RequestWrapper(req);
            }
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

    private Boolean isFiltered(String path) {
        PathMatcher pathMatcher = new AntPathMatcher();
        if (CollectionUtils.isEmpty(WHITE_LIST)) {
            return true;
        }
        for (String whitePath : WHITE_LIST) {
            if (pathMatcher.match(whitePath,path)) {
                return false;
            }
        }
        return true;
    }

}
