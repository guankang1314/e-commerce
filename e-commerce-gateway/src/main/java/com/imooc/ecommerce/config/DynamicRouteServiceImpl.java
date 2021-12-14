package com.imooc.ecommerce.config;


import java.util.List;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author qingtian
 * @description:事件推送 Aware ：动态更新路由网关 Service
 * @Package com.imooc.ecommerce.config
 * @date 2021/12/7 15:55
 */
@Slf4j
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    /**
     * 写路由定义
     */
    private final RouteDefinitionWriter routeDefinitionWriter;

    /**
     * 获取路由定义
     */
    private final RouteDefinitionLocator routeDefinitionLocator;

    /**
     * 事件发布
     */
    private ApplicationEventPublisher publisher;

    public DynamicRouteServiceImpl(RouteDefinitionWriter routeDefinitionWriter
                        ,RouteDefinitionLocator routeDefinitionLocator) {
        this.routeDefinitionWriter = routeDefinitionWriter;
        this.routeDefinitionLocator = routeDefinitionLocator;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

        this.publisher = applicationEventPublisher;
    }

    /**
     * 增加路由定义
     * @param routeDefinition
     * @return
     */
    public String addRouteDefinition(RouteDefinition routeDefinition) {

        log.info("gateway add route : [{}]",routeDefinition);

        //保存路由配置并发布
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        //发布事件通知给gateway
        this.publisher.publishEvent(new RefreshRoutesEvent(this));

        return "success";
    }

    /**
     * 根据路由id删除路由
     * @param id
     * @return
     */
    private String deleteById(String id) {

        try {
            log.info("gateway delete route id : [{}]",id);
            routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception ex) {
            log.error("gateway delete route fail : [{}]",ex.getMessage(),ex);
            return "delete dail";
        }
    }

    /**
     * 更新路由
     * @param definition
     * @return
     */
    private String updateByRouteDefinition(RouteDefinition definition) {

        try {
            log.info("gateway update route : [{}]",definition);
            //删除
            routeDefinitionWriter.delete(Mono.just(definition.getId()));
        }catch (Exception ex) {
            log.error("update fail ,not find routeId : [{}]",definition.getId());
            return "update fail"+definition.getId();
        }

        try {
            log.info("gateway update route save : [{}]",definition);
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception exception) {
            log.error("update fail : [{}]",definition.getId());
            return "update fail";
        }
    }

    /**
     * 更新路由公有方法
     * @param definitions
     * @return
     */
    public String updateList(List<RouteDefinition> definitions) {

        log.info("gateway update route : [{}]",definitions);

        //先拿到当前gateway中存储的路由定义
        List<RouteDefinition> routeDefinitionsExist = routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();
        if (!CollectionUtils.isEmpty(routeDefinitionsExist)) {
            //清除之前所有配置的旧配置
            routeDefinitionsExist.forEach(rd -> {
                log.info("delete route definition : [{}]",rd);
                deleteById(rd.getId());
            });
        }

        //把更新的路由定义同步到gateway中
        definitions.forEach(definition -> updateByRouteDefinition(definition));
        return "success";
    }
}
