package com.imooc.ecommerce.stream;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.vo.GuankMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * @author qingtian
 * @version 1.0
 * @description: 使用默认的信道实现消息的接收
 * @date 2022/5/3 9:24
 */
@Slf4j
@EnableBinding(Sink.class)
public class DefaultReceiveService {

    /**
     *
     * @param payload
     */
    @StreamListener(Sink.INPUT)
    public void receiveMessage(@Payload Object payload) {
        log.info("in defaultReceiveService consume message start");
        GuankMessage guankMessage = JSON.parseObject(payload.toString(), GuankMessage.class);
        log.info("in defaultReceiveService consume message success : [{}]",JSON.toJSONString(guankMessage));
    }
}
