package com.imooc.ecommerce.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.imooc.ecommerce.block_handler.GuankBlockHandler;
import com.imooc.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Guank
 * @version 1.0
 * @description: 基于 sentinel 控制台设置流控规则，snetinel 是懒加载的 先去访问一下就可以在 dashboard 中看见
 * @date 2022-07-24 7:10
 */
@Slf4j
@RestController
@RequestMapping("/dashboard")
public class RateLimitController {

    /**
     * 在 dashboard 中新增流控规则 根据资源名称新增流控规则
     * @return
     */
    @GetMapping("/by-resource")
    @SentinelResource(value = "byResource",blockHandler = "guankHandlerBlockException", blockHandlerClass = GuankBlockHandler.class)
    public CommonResponse<String> byResource() {
        log.info("coming in rate limit controller by resource");
        return new CommonResponse<>(0,"by resource");
    }

    /**
     * 在簇点链路中 给 url 添加流控规则
     * @return
     */
    @GetMapping("/by-url")
    @SentinelResource(value = "byUrl")
    public CommonResponse<String> byUrl() {
        log.info("coming in rate limit controller by url");
        return new CommonResponse<>(0,"by url");
    }

    @PostMapping("/upload")
    public CommonResponse<String> upload(@RequestParam("id") String id, @RequestParam("file")MultipartFile file) {
        return new CommonResponse<>(0,"success");
    }
}
