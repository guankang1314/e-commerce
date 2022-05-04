package com.imooc.ecommerce.partition;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.vo.GuankMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author qingtian
 * @version 1.0
 * @description: 自定义从 message 中提取 PartitionKey 的策略
 * @date 2022/5/4 22:01
 */
@Slf4j
@Component
public class GuankPartitionKeyExtractorStrategy implements PartitionKeyExtractorStrategy {
    @Override
    public Object extractKey(Message<?> message) {
        GuankMessage guankMessage = JSON.parseObject(message.getPayload().toString(), GuankMessage.class);

        //自定义提取 key
        String key = guankMessage.getProjectName();
        log.info("SpringCloud Stream guank partition key : [{}]",key);
        return key;
    }
}
