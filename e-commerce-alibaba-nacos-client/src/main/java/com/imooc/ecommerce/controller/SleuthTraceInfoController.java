package com.imooc.ecommerce.controller;

import com.imooc.ecommerce.service.SleuthTraceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qingtian
 * @version 1.0
 * @description: 打印跟踪信息
 * @date 2021/12/28 8:01
 */
@Slf4j
@RestController
@RequestMapping("/sleuth")
public class SleuthTraceInfoController {

    @Autowired
    private SleuthTraceInfoService sleuthTraceInfoService;

    /**
     * @description: 打印日志跟踪信息
     * @param:
     * @return: void
     * @date: 2021/12/28 8:03
     */
    @GetMapping("/trace-info")
    public void logCurrentTraceInfo() {
        sleuthTraceInfoService.logCurrentTraceInfo();
    }
}
