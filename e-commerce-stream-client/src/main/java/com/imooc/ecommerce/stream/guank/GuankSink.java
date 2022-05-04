package com.imooc.ecommerce.stream.guank;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author qingtian
 * @version 1.0
 * @description: 自定义输入信道
 * @date 2022/5/4 9:14
 */
public interface GuankSink {

    String INPUT = "guankInput";

    /**
     * 输入信道的名称是 guankInput ， 需要使用 Stream 绑定器在 yml 文件中进行绑定
     * @return
     */
    @Input(GuankSink.INPUT)
    SubscribableChannel guankInput();
}
