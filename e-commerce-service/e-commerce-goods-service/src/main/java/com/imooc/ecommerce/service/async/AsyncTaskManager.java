package com.imooc.ecommerce.service.async;

import com.imooc.ecommerce.constant.AsyncTaskStatusEnum;
import com.imooc.ecommerce.goods.GoodsInfo;
import com.imooc.ecommerce.vo.AsyncTaskInfo;
import jdk.internal.net.http.LineSubscriberAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author qingtian
 * @version 1.0
 * @description: 异步任务执行管理器，对异步任务进行包装管理，记录并塞入信息
 * @date 2022/3/1 23:50
 */
@Slf4j
@Component
public class AsyncTaskManager {

    /**
     * 异步任务执行信息容器
     */
    private final Map<String, AsyncTaskInfo> taskContainer = new HashMap<>(16);

    @Autowired
    private IAsyncService asyncService;

    /**
     * 初始化异步任务
     * @return
     */
    public AsyncTaskInfo initTask() {

        AsyncTaskInfo taskInfo = new AsyncTaskInfo();
        //设置一个唯一的异步任务id
        taskInfo.setTaskId(UUID.randomUUID().toString());
        taskInfo.setStatus(AsyncTaskStatusEnum.START);
        taskInfo.setStartTime(new Date());

        //初始化的时候就要把异步任务执行信息放入到存储容器中
        taskContainer.put(taskInfo.getTaskId(),taskInfo);
        return taskInfo;
    }

    /**
     * 提交异步任务
     * @param goodsInfos
     * @return
     */
    public AsyncTaskInfo submit(List<GoodsInfo> goodsInfos) {

        //初始化一个异步任务监控信息
        AsyncTaskInfo taskInfo = initTask();
        asyncService.asyncImportGoods(goodsInfos,taskInfo.getTaskId());
        return taskInfo;
    }

    /**
     * 设置异步任务的执行信息
     * @param taskInfo
     */
    public void setTaskInfo(AsyncTaskInfo taskInfo) {
        taskContainer.put(taskInfo.getTaskId(),taskInfo);
    }

    /**
     * 获取异步任务的执行状态信息
     * @param taskId
     */
    public AsyncTaskInfo getTaskInfo(String taskId) {
        return taskContainer.get(taskId);
    }
}
