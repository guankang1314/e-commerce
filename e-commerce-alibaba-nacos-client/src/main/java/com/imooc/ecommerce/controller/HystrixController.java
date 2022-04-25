package com.imooc.ecommerce.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.service.NacosClientService;
import com.imooc.ecommerce.service.hysrix.CacheHystrixCommand;
import com.imooc.ecommerce.service.hysrix.NacosClientHystrixCommand;
import com.imooc.ecommerce.service.hysrix.NacosClientHystrixObservableCommand;
import com.imooc.ecommerce.service.hysrix.UseHystrixCommandAnnotation;
import com.imooc.ecommerce.service.hysrix.request_merge.NacosClientCollapseCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author qingtian
 * @version 1.0
 * @description: TODO
 * @date 2022/4/3 0:31
 */
@Slf4j
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @Autowired
    private UseHystrixCommandAnnotation useHystrixCommandAnnotation;

    @Autowired
    private NacosClientService nacosClientService;

    @GetMapping("/hystrix-command-annotation")
    public List<ServiceInstance> getNacosClientInfo(@RequestParam String serviceId) {
        log.info("request nacos client info annotation : [{}],[{}]",serviceId,Thread.currentThread().getName());
        return useHystrixCommandAnnotation.getNacosClientInfo(serviceId);
    }

    @GetMapping("/simple-hystrix-command")
    public List<ServiceInstance> getServiceInstanceByServiceId(@RequestParam String serviceId) throws ExecutionException, InterruptedException {

        //第一种方式  同步阻塞的方式
        List<ServiceInstance> serviceInstances01 = new NacosClientHystrixCommand(nacosClientService, serviceId)
                .execute();
        log.info("use execute to get service instance : [{}], [{}]",
                JSON.toJSONString(serviceInstances01),Thread.currentThread().getName());

        //第二种方式  异步非阻塞
        List<ServiceInstance> serviceInstances02;
        Future<List<ServiceInstance>> future = new NacosClientHystrixCommand(nacosClientService, serviceId).queue();
        //做一些别的事情
        serviceInstances02 = future.get();
        log.info("use execute to get service instance : [{}], [{}]",
                JSON.toJSONString(serviceInstances02),Thread.currentThread().getName());

        //第三种方式  热响应调用
        Observable<List<ServiceInstance>> observable = new NacosClientHystrixCommand(nacosClientService, serviceId).observe();
        //热响应调用，在开始只会创建线程而不会触发方法的调用，等到使用 observable.toBlocking().single() 方法的时候才会真正去调用方法
        List<ServiceInstance> serviceInstances03 = observable.toBlocking().single();
        log.info("use execute to get service instance : [{}], [{}]",
                JSON.toJSONString(serviceInstances03),Thread.currentThread().getName());

        //第四种方式，异步冷响应调用
        Observable<List<ServiceInstance>> toObservable = new NacosClientHystrixCommand(nacosClientService, serviceId).toObservable();
        //异步冷响应调用，主要与第三种热响应调用的区别，冷响应调用不会创建新的线程 而是在 toObservable.toBlocking().single() 的时候才会创建新的线程调用方法
        List<ServiceInstance> serviceInstances04 = toObservable.toBlocking().single();
        log.info("use execute to get service instance : [{}], [{}]",
                JSON.toJSONString(serviceInstances04),Thread.currentThread().getName());
        return serviceInstances01;
    }

    @GetMapping("/hystrix-observable-command")
    public List<ServiceInstance> getServiceInstanceByServiceIdObservable(@RequestParam String serviceId) {

        List<String> serviceIds = Arrays.asList(serviceId, serviceId, serviceId);
        List<List<ServiceInstance>> result = new ArrayList<>(serviceIds.size());

        NacosClientHystrixObservableCommand observableCommand = new NacosClientHystrixObservableCommand(nacosClientService, serviceIds);

        //异步执行命令
        Observable<List<ServiceInstance>> observe = observableCommand.observe();

        //注册获取结果
        observe.subscribe(new Observer<List<ServiceInstance>>() {
            @Override
            public void onCompleted() {
                log.info("all tasks is complete : [{}], [{}]", serviceId, Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(List<ServiceInstance> serviceInstances) {
                result.add(serviceInstances);
            }
        });
        log.info("observable command result is : [{}], [{}]", JSON.toJSONString(result), Thread.currentThread().getName());
        return result.get(0);
    }

    @GetMapping("/cache-hystrix-command")
    public void cacheHystrixCommand(@RequestParam String serviceId) {
        //使用缓存 command ，发起两次请求
        CacheHystrixCommand command1 = new CacheHystrixCommand(nacosClientService, serviceId);
        CacheHystrixCommand command2 = new CacheHystrixCommand(nacosClientService, serviceId);

        List<ServiceInstance> result01 = command1.execute();
        List<ServiceInstance> result02 = command2.execute();
        log.info("result01, result02 : [{}], [{}]",JSON.toJSONString(result01),JSON.toJSONString(result02));

        //清除缓存
        CacheHystrixCommand command3 = new CacheHystrixCommand(nacosClientService, serviceId);
        CacheHystrixCommand command4 = new CacheHystrixCommand(nacosClientService, serviceId);

        List<ServiceInstance> result03 = command3.execute();
        List<ServiceInstance> result04 = command4.execute();
        log.info("result03, result04 : [{}], [{}]",JSON.toJSONString(result03),JSON.toJSONString(result04));
    }

    /**
     * 编程方式实现请求合并
     * @throws Exception
     */
    @GetMapping("/request-merge")
    public void requestMerge() throws Exception {
        NacosClientCollapseCommand collapseCommand01 = new NacosClientCollapseCommand(
                nacosClientService, "e-commerce-nacos-client1");
        NacosClientCollapseCommand collapseCommand02 = new NacosClientCollapseCommand(
                nacosClientService, "e-commerce-nacos-client2");
        NacosClientCollapseCommand collapseCommand03 = new NacosClientCollapseCommand(
                nacosClientService, "e-commerce-nacos-client3");

        Future<List<ServiceInstance>> future01 = collapseCommand01.queue();
        Future<List<ServiceInstance>> future02 = collapseCommand02.queue();
        Future<List<ServiceInstance>> future03 = collapseCommand03.queue();

        future01.get();
        future02.get();
        future03.get();

        Thread.sleep(2000);

        //过了合并请求的时间
        NacosClientCollapseCommand collapseCommand04 = new NacosClientCollapseCommand(
                nacosClientService, "e-commerce-nacos-client4");
        Future<List<ServiceInstance>> future04 = collapseCommand04.queue();
        future04.get();
    }
}
