package com.imooc.ecommerce.conf;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.vo.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

/**
 * @author Guank
 * @version 1.0
 * @description: RestTemplate 在限流异常时的兜底方法
 * @date 2022-07-24 7:38
 */
@Slf4j
public class RestTemplateExceptionUtil {

    /**
     * 限流之后的处理方法
     * @param request
     * @param body
     * @param execution
     * @param exception
     * @return
     */
    public static SentinelClientHttpResponse handleBlock(HttpRequest request,
                                                         byte[] body,
                                                         ClientHttpRequestExecution execution,
                                                         BlockException exception) {
        log.error("Handle RestTemplate Block Exception : [{}], [{}]",
                request.getURI().getPath(),exception.getClass().getCanonicalName(),exception);
        return new SentinelClientHttpResponse(JSON.toJSONString(new JwtToken("guank-block")));
    }

    /**
     * 异常降级之后的处理方法
     * @param request
     * @param body
     * @param execution
     * @param exception
     * @return
     */
    public static SentinelClientHttpResponse handleFallback(HttpRequest request,
                                                            byte[] body,
                                                            ClientHttpRequestExecution execution,
                                                            BlockException exception) {
        log.error("Handle RestTemplate Fallback Exception : [{}], [{}]",
                request.getURI().getPath(),exception.getClass().getCanonicalName(),exception);
        return new SentinelClientHttpResponse(JSON.toJSONString(new JwtToken("guank-fallback")));
    }
}
