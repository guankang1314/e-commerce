package com.imooc.ecommerce.vo;

import com.imooc.ecommerce.constant.AsyncTaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author qingtian
 * @version 1.0
 * @description: 标记异步任务执行信息
 * @date 2022/2/23 0:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsyncTaskInfo {

    /**
     * 异步任务id
     */
    private String taskId;

    /**
     * 异步任务执行状态
     */
    private AsyncTaskStatusEnum status;

    /**
     * 异步任务开始时间
     */
    private Date startTime;

    /**
     * 异步任务结束时间
     */
    private Date endTime;

    /**
     * 异步任务总耗时
     */
    private String totalTime;
}
