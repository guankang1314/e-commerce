package com.imooc.ecommerce.feign;

import com.imooc.ecommerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Guank
 * @version 1.0
 * @description: 通过 Sentinel 对 openFeign 实现熔断降级
 * @date 2022-07-30 16:15
 */
@FeignClient(
        value = "e-commrece-imooc",
        fallback = SentinelFeignClientFallback.class
)
public interface SentinelFeignClient {

    @RequestMapping(value = "guank",method = RequestMethod.GET)
    CommonResponse<String> getResultByFeign(@RequestParam Integer code);
}
