package com.imooc.ecommerce.service.hysrix.request_merge;

import com.imooc.ecommerce.service.NacosClientService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qingtian
 * @version 1.0
 * @description: 请求合并器
 * @date 2022/4/25 21:11
 */
@Slf4j
public class NacosClientCollapseCommand extends HystrixCollapser<List<List<ServiceInstance>>, List<ServiceInstance>, String> {

    private final NacosClientService nacosClientService;
    private final String serviceId;

    public NacosClientCollapseCommand(NacosClientService nacosClientService, String serviceId) {
        //初始化请求合并器
        super(
                HystrixCollapser.Setter.withCollapserKey(
                        HystrixCollapserKey.Factory.asKey("NacosClientCollapseCommand")
                ).andCollapserPropertiesDefaults(
                        //300毫秒内合并请求，300毫秒后的请求需要重新生成请求合并器
                        HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(300)
                )
        );

        this.nacosClientService = nacosClientService;
        this.serviceId = serviceId;
    }

    /**
     * 获取请求参数
     * @return
     */
    @Override
    public String getRequestArgument() {
        return this.serviceId;
    }

    /**
     * 创建批量请求的 Hystrix Command
     * @param collection
     * @return
     */
    @Override
    protected HystrixCommand<List<List<ServiceInstance>>> createCommand(
            Collection<CollapsedRequest<List<ServiceInstance>, String>> collection) {

        List<String> serviceIds = new ArrayList<>(collection.size());
        serviceIds.addAll(
                collection.stream()
                        .map(CollapsedRequest::getArgument)
                        .collect(Collectors.toList())
        );
        return new NacosClientBatchCommand(nacosClientService,serviceIds);
    }

    /**
     * 响应分发给单独的请求, 将list中的结果分给每一个单独的请求
     * @param lists
     * @param collection
     */
    @Override
    protected void mapResponseToRequests(List<List<ServiceInstance>> lists,
                                         Collection<CollapsedRequest<List<ServiceInstance>,
                                                 String>> collection) {
        //返回的响应都是根据请求顺序包装的，只要按顺序返回给响应的请求就行
        int count = 0;
        for (CollapsedRequest<List<ServiceInstance>, String> collapsedRequest : collection) {
            //从批量响应中按顺序取出结果
            List<ServiceInstance> instances = lists.get(count++);
            //将结果放回原来的响应中
            collapsedRequest.setResponse(instances);
        }
    }
}
