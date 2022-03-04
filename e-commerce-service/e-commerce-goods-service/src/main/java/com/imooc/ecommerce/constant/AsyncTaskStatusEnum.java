package com.imooc.ecommerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.kafka.common.protocol.types.Field;

/**
 * @author qingtian
 * @version 1.0
 * @description: 异步任务状态枚举
 * @date 2022/3/1 23:45
 */
@Getter
@AllArgsConstructor
public enum AsyncTaskStatusEnum {

    START(0,"已经启动"),
    RUNNING(1,"正在运行"),
    SUCCESS(2,"执行成功"),
    FAILED(3,"执行失败"),
    ;

    /**
     * 执行状态编码
     */
    private final int state;

    /**
     * 执行状态描述
     */
    private final String stateInfo;
}
