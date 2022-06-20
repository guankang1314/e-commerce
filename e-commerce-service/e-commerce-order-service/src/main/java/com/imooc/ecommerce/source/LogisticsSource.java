package com.imooc.ecommerce.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Guank
 * @version 1.0
 * @description: 自定义物流消息通信信道
 * @date 2022/6/19 19:38
 */
public interface LogisticsSource {

    /**
     * 输出信道的名称
     */
    String OUTPUT = "logisticsOutput";

    /**
     *  物流 Source -> logisticsOutput
     *  通信信道的名称 logisticsOutput 对应的是 yml 文件里的配置
     * @return
     */
    @Output(LogisticsSource.OUTPUT)
    MessageChannel logisticsOutput();
}
