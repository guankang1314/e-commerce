package com.imooc.ecommerce.service.hysrix.request_merge;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.service.NacosClientService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;

import java.util.Collections;
import java.util.List;

/**
 * @author qingtian
 * @version 1.0
 * @description: 批量请求 hystrix Command
 * @date 2022/4/24 23:20
 */
@Slf4j
public class NacosClientBatchCommand extends HystrixCommand<List<List<ServiceInstance>>> {

    private final NacosClientService nacosClientService;
    private final List<String> serviceIds;

    public NacosClientBatchCommand(NacosClientService nacosClientService, List<String> serviceIds) {
        super(
                HystrixCommand.Setter.withGroupKey(
                        HystrixCommandGroupKey.Factory.asKey("NacosClientBatchCommand")
                )
        );
        this.nacosClientService = nacosClientService;
        this.serviceIds = serviceIds;
    }

    @Override
    protected List<List<ServiceInstance>> run() throws Exception {
        log.info("use nacos client batch command to get result : [{}]", JSON.toJSONString(serviceIds));
        return nacosClientService.getNacosInfos(serviceIds);
    }

    @Override
    protected List<List<ServiceInstance>> getFallback() {
        log.warn("nacos client batch command failure , use fallback");
        return Collections.emptyList();
    }
}
