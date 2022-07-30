package com.imooc.ecommerce.controller;

import com.imooc.ecommerce.feign.SentinelFeignClient;
import com.imooc.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Guank
 * @version 1.0
 * @description: openfeign 集成 Sentinel 实现熔断降级
 * @date 2022-07-30 19:12
 */
@RestController
@Slf4j
@RequestMapping("/sentinel-feign")
public class SentinelFeignController {

    @Autowired
    private SentinelFeignClient sentinelFeignClient;

    /**
     * 通过 feign 接口获取结果
     * @param code
     * @return
     */
    @GetMapping("/result-by-feign")
    public CommonResponse<String> getResultByFeign(@RequestParam Integer code) {
        log.info("coming in get result by fiegn : [{}]",code);
        return sentinelFeignClient.getResultByFeign(code);
    }
}
