package com.imooc.ecommerce.stream.guank;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.vo.GuankMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * @author qingtian
 * @version 1.0
 * @description: 使用自定义的输入信道 接收消息
 * @date 2022/5/4 9:17
 */
@Slf4j
@EnableBinding(GuankSink.class)
public class GuankReceiveService {

    /**
     * 使用自定义的输入信道接收消息
     * @param payload
     */
    @StreamListener(GuankSink.INPUT)
    public void receiveMessage(@Payload Object payload) {
        log.info("in guankReceiveService consume message start");
        GuankMessage guankMessage = JSON.parseObject(payload.toString(), GuankMessage.class);
        log.info("in guankReceiveService consume message success : [{}]",JSON.toJSONString(guankMessage));
    }
}
