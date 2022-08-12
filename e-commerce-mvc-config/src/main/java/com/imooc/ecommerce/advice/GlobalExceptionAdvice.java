package com.imooc.ecommerce.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.imooc.ecommerce.vo.CommonResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.ecommerce.advice
 * @date 2021/11/30 23:03
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public CommonResponse<String> handlerCommerceException(
            HttpServletRequest req,Exception ex) {

        CommonResponse<String> res = new CommonResponse<>(-1, "business error");
        res.setData(ex.getMessage());
        log.error("commerce service has error:[{}]",ex.getMessage(),ex);
        return res;
    }
}
