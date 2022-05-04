package com.imooc.ecommerce.partition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.PartitionSelectorStrategy;
import org.springframework.stereotype.Component;

/**
 * @author qingtian
 * @version 1.0
 * @description: 决定 payload 发送到哪个分区的策略
 * @date 2022/5/4 22:04
 */
@Slf4j
@Component
public class GuankPartitionSelectorStrategy implements PartitionSelectorStrategy {

    /**
     * 选择分区的策略
     * @param key
     * @param partitionCount
     * @return
     */
    @Override
    public int selectPartition(Object key, int partitionCount) {
        int partition = key.toString().hashCode() % partitionCount;
        log.info("SpringCloud Stream guank Selector info : [{}], [{}], [{}]",
                key, partitionCount, partition);
        return partition;
    }
}
