package com.imooc.ecommerce.filter;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author qingtian
 * @version 1.0
 * @description: 初始化 Hystrix 请求上下文环境
 * @date 2022/4/21 23:18
 */
@Slf4j
@Component
@WebFilter(
        filterName = "HystrixRequestContextServletFilter",
        urlPatterns = "/*",
        asyncSupported = true
)
public class HystrixRequestContextServletFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        //初始化 Hystrix 请求上下文
        //在不同的 context 中缓存是不共享的
        //这个初始化是必须的
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        try {
            //配置 hystrix 缓存策略
            hystrixConcurrencyStrategyConfig();
            //请求正常通过
            filterChain.doFilter(servletRequest,servletResponse);
        }finally {
            //关闭 Hystrix 请求上下文
            context.shutdown();
        }
    }

    /**
     * 配置 Hystrix 的并发策略
     * 为什么要配置?
     * 因为在项目中引入了sleuth, sleuth会有一个默认的 Hystrix
     * 并发策略: SleuthHystrixConcurrencyStrategy 我们不需要
     * */
    public void hystrixConcurrencyStrategyConfig() {
        try {
            HystrixConcurrencyStrategy target = HystrixConcurrencyStrategyDefault.getInstance();
            //得到当前的 Hystrix 缓存策略
            HystrixConcurrencyStrategy strategy = HystrixPlugins.getInstance().getConcurrencyStrategy();
            if (strategy instanceof HystrixConcurrencyStrategyDefault) {
                //如果是我们想要的 直接返回
                return;
            }

            //将原来的其他配置保存下来
            HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance().getCommandExecutionHook();
            HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
            HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
            HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance().getPropertiesStrategy();

            //先重置 再把我自定义的配置与原来的配置写回去
            HystrixPlugins.reset();
            HystrixPlugins.getInstance().registerConcurrencyStrategy(target);
            HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
            HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
            HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
            HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);

            log.info("config hystrix concurrency strategy success");
        }catch (Exception e) {
            log.error("failed to register Hystrix Concurrency Strategy : [{}]",e.getMessage(),e);
        }
    }
}
