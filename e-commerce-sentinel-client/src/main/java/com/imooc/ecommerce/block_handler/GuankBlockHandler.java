package com.imooc.ecommerce.block_handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Guank
 * @version 1.0
 * @description: 自定义通用的限流处理逻辑
 * @date 2022-07-17 16:12
 */
@Slf4j
public class GuankBlockHandler {

    /**
     * 通用限流处理方法
     * 这个方法必须是 static 的
     * @param exception
     * @return
     */
    public static CommonResponse<String> guankHandlerBlockException(BlockException exception) {
        log.error("trigger guank block handler : [{}], [{}]",
                JSON.toJSONString(exception.getRule()),exception.getRuleLimitApp());
        return new CommonResponse<>(-1,
                "flow rule trigger block exception",
                null);
    }
}
