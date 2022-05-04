package com.imooc.ecommerce.stream.guank;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.vo.GuankMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author qingtian
 * @version 1.0
 * @description: 使用自定义的通信信道，指定使用的信道
 * @date 2022/5/4 9:10
 */
@Slf4j
@EnableBinding(GuankSource.class)
public class GuankSendService {
    @Autowired
    private GuankSource guankSource;

    /**
     * 使用自定义的信道发送消息
     * @param message
     */
    public void sendMessage(GuankMessage message) {
        String _message = JSON.toJSONString(message);
        log.info("in guankSendService send message : [{}]",_message);
        guankSource.guankOutput().send(MessageBuilder.withPayload(_message).build());
    }
}
