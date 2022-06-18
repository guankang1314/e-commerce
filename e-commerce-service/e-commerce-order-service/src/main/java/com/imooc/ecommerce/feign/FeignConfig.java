package com.imooc.ecommerce.feign;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author Guank
 * @version 1.0
 * @description: feign 调用时，把 Header 也传递到服务提供方
 * @date 2022/6/15 8:15
 */
@Slf4j
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor headerInterceptor() {

        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != attributes) {
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (null != headerNames) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String values = request.getHeader(name);
                        // 不能把当前请求的 content-length 传递到下游的服务提供方，这明显是不对的
                        // 当前的请求和接下来的请求长度基本不可能是一致的 会导致请求可能一直返回不了 或是响应被截断
                        //所以要将 content-length 这个属性 忽略掉
                        if (!"content-length".equalsIgnoreCase(name)) {
                            //这里的 requestTemplate 就是 restTemplate
                            requestTemplate.header(name,values);
                        }
                    }
                }
            }
        };
    }
}
