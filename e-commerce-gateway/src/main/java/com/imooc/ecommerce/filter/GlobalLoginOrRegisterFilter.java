package com.imooc.ecommerce.filter;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.constant.CommonConstant;
import com.imooc.ecommerce.constant.GatewayConstant;
import com.imooc.ecommerce.util.TokenParseUtil;
import com.imooc.ecommerce.vo.JwtToken;
import com.imooc.ecommerce.vo.LoginUserInfo;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author qingtian
 * @version 1.0
 * @description: 全局鉴权过滤器
 * @date 2021/12/21 23:22
 */
@Component
@Slf4j
public class GlobalLoginOrRegisterFilter implements GlobalFilter, Ordered {

    /**
     * @description: 从注册中心中获取对应服务的实例信息
     * @date 2021/12/21 23:42
     */
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * @description: 登录，注册，鉴权
     * 1. 如果是登录或注册，则去注册中心拿到token并返回给客户端
     * 2. 如果是访问其他的服务，则鉴权，没有权限返回401
     * @param: exchange
     * @param: chain
     * @return: reactor.core.publisher.Mono<java.lang.Void>
     * @date: 2021/12/22 0:17
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //如果是登录
        if (request.getURI().getPath().contains(GatewayConstant.LOGIN_URI)) {
            //去授权中心拿token
            String token = getTokenFromAuthorityCenter(
                    request,GatewayConstant.AUTHORITY_CENTER_TOKEN_URL_FORMAT
            );
            //header 中不能设置null
            response.getHeaders().add(
                    CommonConstant.JWT_USER_INFO_KEY,
                    null == token ? "null" : token
            );
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        }

        //如果是注册
        if (request.getURI().getPath().contains(GatewayConstant.REGISTER_URI)) {
            //去注册中心拿token: 会先创建用户，在返回token
            String token = getTokenFromAuthorityCenter(
                    request,GatewayConstant.AUTHORITY_CENTER_REGISTER_URL_FORMAT
            );
            //header中不允许为null对象
            response.getHeaders().add(
                    CommonConstant.JWT_USER_INFO_KEY,
                    null == token ? "null" : token
            );
            response.setStatusCode(HttpStatus.OK);
            response.setComplete();
        }

        //带着token去访问其他的服务，则鉴权
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(CommonConstant.JWT_USER_INFO_KEY);
        LoginUserInfo loginUserInfo = null;

        try {
            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        } catch (Exception ex) {
            log.error("parse user info from token has error : [{}]",ex.getMessage(),ex);
        }

        //获取不到用户信息
        if (null == loginUserInfo) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //解析通过放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 2;
    }

    /**
     * @description:  从post请求中获取到数据
     * @param: request
     * @return: java.lang.String
     * @date: 2021/12/21 23:35
     */
    private String parseBodyFromRequest(ServerHttpRequest request) {

        //获取请求体
        Flux<DataBuffer> body = request.getBody();
        //获取原子引用
        AtomicReference<String> atomRef = new AtomicReference<>();

        //订阅缓冲去去消费请求体中的数据
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            //由于在之前的GlobalCacheRequestBodyFilter中将Body进行了retain所以在这里必须要释放掉
            //否则会造成内存泄露
            DataBufferUtils.release(buffer);
            atomRef.set(charBuffer.toString());
        });

        //获取到request body
        return atomRef.get();
    }

    /**
     * @description: 从授权中心获取 token
     * @param: request
     * @param: urlFormat
     * @return: java.lang.String
     * @date: 2021/12/21 23:38
     */
    private String getTokenFromAuthorityCenter(ServerHttpRequest request,String urlFormat) {

        //负载均衡
        ServiceInstance serviceInstance = loadBalancerClient.choose(CommonConstant.AUTHORITY_CENTER_SERVICE_ID);
        log.info("Nacos Client Info : [{}],[{}],[{}]",
                serviceInstance.getServiceId(),serviceInstance.getInstanceId(),
                JSON.toJSONString(serviceInstance.getMetadata()));
        //拼接请求的url
        String requestUrl = String.format(
                urlFormat,serviceInstance.getHost(),serviceInstance.getPort()
        );
        UsernameAndPassword requestBody = JSON.parseObject(
                parseBodyFromRequest(request),UsernameAndPassword.class
        );
        log.info("login request url and body : [{}],[{}]",requestUrl,JSON.toJSONString(requestBody));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JwtToken token = restTemplate.postForObject(
                requestUrl,
                new HttpEntity<>(JSON.toJSONString(requestBody),headers),
                JwtToken.class
        );

        if (null != token) {
            return token.getToken();
        }
        return null;
    }
}
