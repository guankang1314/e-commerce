package com.imooc.ecommerce.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.imooc.ecommerce.annotation.IgnoreResponseAdvice;
import com.imooc.ecommerce.vo.CommonResponse;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.ecommerce.advice
 * @date 2021/11/30 22:52
 */
@RestControllerAdvice(value = "com.imooc.ecommerce")
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 判断响应是否需要被包装
     */
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {

        //如果被IgnoreResponseAdvice注解进行标记则不需要对返回的响应体做处理
        if (methodParameter.getDeclaringClass()
                .isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        if (methodParameter.getMethod()
                .isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                    MediaType mediaType,
                                    Class<? extends HttpMessageConverter<?>> aClass,
                                    ServerHttpRequest serverHttpRequest,
                                    ServerHttpResponse serverHttpResponse) {

        //定义最终返回的对象
        CommonResponse<Object> response = new CommonResponse<>(0,"");

        if (null == o) {
            return response;
        }else if (o instanceof CommonResponse) {
            response = (CommonResponse<Object>) o;
        }else {
            response.setData(o);
        }
        return response;
    }
}
