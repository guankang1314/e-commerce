package com.imooc.ecommerce.service;

import brave.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qingtian
 * @version 1.0
 * @description: 使用代码更直观的看到Sleuth生成的相关跟踪信息
 * @date 2021/12/28 0:36
 */
@Service
@Slf4j
public class SleuthTraceInfoService {

    /**
     * @description: brave.Tracer跟踪对象
     * @date 2021/12/28 0:38
     */
    @Autowired
    private Tracer tracer;

    /**
     * @description: 打印当前的跟踪信息到日志中
     * @param:
     * @return: void
     * @date: 2021/12/28 0:39
     */
    public void logCurrentTraceInfo() {

        log.info("Sleuth trace id : [{}]",tracer.currentSpan().context().traceId());
        log.info("Sleuth span id : [{}]",tracer.currentSpan().context().spanId());
    }
}
