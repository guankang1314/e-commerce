package com.imooc.ecommerce.service.async;

import com.imooc.ecommerce.constant.AsyncTaskStatusEnum;
import com.imooc.ecommerce.vo.AsyncTaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Date;
import java.util.Objects;

/**
 * @author qingtian
 * @version 1.0
 * @description: 异步任务执行监控切面
 * @date 2022/3/5 0:23
 */
@Slf4j
@Aspect
@Component
public class AsyncTaskMonitor {

    /**
     * 异步任务执行管理器
     */
    @Autowired
    private AsyncTaskManager manager;


    @Pointcut("execution(* com.imooc.ecommerce.service.async.AsyncServiceImpl.*(..))")
    public void pointCut() {}

    /**
     * 环绕切面可以在方法执行前和执行后做额外操作
     * @param proceedingJoinPoint
     * @return
     */
    @Around("pointCut()")
    public Object taskHandle(ProceedingJoinPoint proceedingJoinPoint) {

        //获取 taskId, 调用异步任务的第二个参数
        String taskId = proceedingJoinPoint.getArgs()[1].toString();

        //获取任务信息，在提交任务的时候就放入容器中
        AsyncTaskInfo taskInfo = manager.getTaskInfo(taskId);
        log.info("AsyncTaskMonitor is monitoring async task : [{}]",taskId);

        taskInfo.setStatus(AsyncTaskStatusEnum.RUNNING);
        manager.setTaskInfo(taskInfo);

        AsyncTaskStatusEnum status;
        Object result;
        try {
            result = proceedingJoinPoint.proceed();
            //如果异步任务执行成功了
            status = AsyncTaskStatusEnum.SUCCESS;
        }catch (Throwable ex) {
            //如果异步任务执行失败了
            result = null;
            status = AsyncTaskStatusEnum.FAILED;
            log.error("AsyncTaskMonitor: async task [{}] is failed, Error Info : [{}]",
                    taskId,ex.getMessage(),ex);
        }

        //设置异步任务其他的信息，放入容器中
        taskInfo.setEndTime(new Date());
        taskInfo.setStatus(status);
        taskInfo.setTotalTime(String.valueOf(taskInfo.getEndTime().getTime() - taskInfo.getStartTime().getTime()));
        manager.setTaskInfo(taskInfo);
        return result;
    }
}
