package com.imooc.ecommerce.feign;

import com.imooc.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Guank
 * @version 1.0
 * @description: Sentinel 对 openfeign 接口的降级策略
 * @date 2022-07-30 16:19
 */
@Slf4j
@Component
public class SentinelFeignClientFallback implements SentinelFeignClient {

    @Override
    public CommonResponse<String> getResultByFeign(Integer code) {
        log.error("request supply for test has some error : [{}]",code);
        return new CommonResponse<>(
                -1,
                "sentinel feign fallback",
                "input code is :" + code
        );
    }
}
