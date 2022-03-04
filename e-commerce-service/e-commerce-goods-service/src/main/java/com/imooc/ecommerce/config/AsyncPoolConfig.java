package com.imooc.ecommerce.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import springfox.documentation.spring.web.json.Json;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author qingtian
 * @version 1.0
 * @description: 自定义异步任务线程池
 * @date 2022/2/23 0:11
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncPoolConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(20);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("guank-Async");

        //等待所有任务结束后关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        //初始化线程池
        executor.initialize();
        return executor;
    }

    /**
     * 指定系统中的异步任务在出现异常时使用到的处理器
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler{

        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
            throwable.printStackTrace();
            log.error("Async Error : [{}],Method : [{}],Params : [{}]",
                    throwable.getMessage(),method.getName(), JSON.toJSONString(objects));

            // TODO 发送邮件或是短信，进入下一步处理
        }
    }
}
