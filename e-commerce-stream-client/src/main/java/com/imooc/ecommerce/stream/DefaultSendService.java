package com.imooc.ecommerce.stream;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.vo.GuankMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author qingtian
 * @version 1.0
 * @description: 使用默认的通信信道发送消息
 * @date 2022/5/3 9:09
 */
@Slf4j
@EnableBinding(Source.class)
public class DefaultSendService {

    /**
     * 注入信道源
     */
    @Autowired
    private Source source;

    public void sendMessage(GuankMessage message) {

        String _message = JSON.toJSONString(message);
        log.info("in DefaultSendService send message : [{}]",_message);

        //Spring Messaging ,统一消息的编程模型，是 Stream 组件的重要组成部分之一
        source.output().send(MessageBuilder.withPayload(_message).build());
    }
}
