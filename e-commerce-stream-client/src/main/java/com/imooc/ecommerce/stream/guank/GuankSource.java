package com.imooc.ecommerce.stream.guank;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author qingtian
 * @version 1.0
 * @description: 自定义输出信道
 * @date 2022/5/4 9:07
 */
public interface GuankSource {

    String OUTPUT = "guankOutput";

    /**
     * 输出信道的名称是 guanKOutput ，需要使用 stream 绑定器绑定
     * @return
     */
    @Output(GuankSource.OUTPUT)
    MessageChannel guankOutput();
}
