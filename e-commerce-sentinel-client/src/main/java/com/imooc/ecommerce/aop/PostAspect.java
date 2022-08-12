package com.imooc.ecommerce.aop;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Guank
 * @version 1.0
 * @description: TODO
 * @date 2022-08-12 11:20
 */
@Component
@Slf4j
@Aspect
@Order(1)
public class PostAspect {

    @Pointcut("execution(* com.imooc.ecommerce.controller..*.*(..))")
    public void pointCut() {}

    private HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes);
        return requestAttributes.getRequest();
    }

    @Before("pointCut()")
    public void Before(JoinPoint joinPoint) throws IOException {
        HttpServletRequest request = getRequest();
        Assert.notNull(request,"request not null !");
        JSONObject postParams = getPostParams(request);
        log.info("request method : [{}], request url : [{}], request args : [{}]",
                request.getMethod(),
                request.getRequestURL(),
                postParams);
    }

    private JSONObject getPostParams(HttpServletRequest request) throws IOException {
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;

        try {
            inputStreamReader = new InputStreamReader(request.getInputStream(),"utf-8");
            reader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = reader.readLine()) != null) {
                stringBuilder.append(inputStr);
            }
            JSONObject jsonObject = JSONObject.parseObject(stringBuilder.toString());
            log.info("json object is : [{}]", JSON.toJSONString(jsonObject));
            return jsonObject;
        }catch (Exception e) {
            throw e;
        }finally {
            IOUtils.closeQuietly(inputStreamReader);
            IOUtils.closeQuietly(reader);
        }
    }
}
