package com.imooc.ecommerce.controller;

import com.imooc.ecommerce.stream.DefaultSendService;
import com.imooc.ecommerce.stream.guank.GuankSendService;
import com.imooc.ecommerce.vo.GuankMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qingtian
 * @version 1.0
 * @description: 构建消息驱动
 * @date 2022/5/4 20:53
 */
@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {


    @Autowired
    private DefaultSendService defaultSendService;

    @Autowired
    private GuankSendService guankSendService;


    /**
     * 默认的信道
     */
    @GetMapping("/default")
    public void defaultSend() {
        defaultSendService.sendMessage(GuankMessage.defaultMessage());
    }

    /**
     * 自定义信道
     */
    @GetMapping("/guank")
    public void guankSend() {
        guankSendService.sendMessage(GuankMessage.defaultMessage());
    }
}
