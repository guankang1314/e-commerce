package com.imooc.ecommerce.filter;

import com.imooc.ecommerce.constant.GatewayConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author qingtian
 * @version 1.0
 * @description: 缓存RequestBody的全局过滤器
 * @date 2021/12/13 18:30
 */
@Slf4j
@Component
public class GlobalCacheRequestBodyFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //判断请求的路径中是否含有login或者是register
        boolean isLoginOrRegister = exchange.getRequest().getURI().getPath().contains(GatewayConstant.LOGIN_URI)
                || exchange.getRequest().getURI().getPath().contains(GatewayConstant.REGISTER_URI);

        if (null == exchange.getRequest().getHeaders().getContentType()
        || !isLoginOrRegister) {
            return chain.filter(exchange);
        }

        //DataBufferUtils.join()拿到请求中的数据
        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {

            //确保数据缓冲区的数据不被释放DataBufferUtils.retain
            DataBufferUtils.retain(dataBuffer);
            //defer just创建数据源，得到当前数据的副本
            Flux<DataBuffer> cachedFlux = Flux.defer(() ->
                    Flux.just(dataBuffer.slice(0,dataBuffer.readableByteCount())));
            //重新包装ServerHttp请求，重写getBody方法,能狗返回请求数据
            ServerHttpRequest mutateRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedFlux;
                }
            };
            //将包装后的serverhttpRequest向下传递
            return chain.filter(exchange.mutate().request(mutateRequest).build());
        });
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 1;
    }
}
