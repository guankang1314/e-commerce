package com.imooc.ecommerce.config;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

/**
 * @author qingtian
 * @description:通过nacos下发动态路由配置，监听naocs中路由配置变更
 * @Package com.imooc.ecommerce.config
 * @date 2021/12/7 16:29
 */
@Component
@Slf4j
@DependsOn({"gatewayConfig"})
public class DynamicRouteServiceImplByNacos {

    /**
     * nacos配置服务
     */
    private ConfigService configService;

    private final DynamicRouteServiceImpl dynamicRouteService;

    public DynamicRouteServiceImplByNacos(DynamicRouteServiceImpl dynamicRouteService) {
        this.dynamicRouteService = dynamicRouteService;
    }

    /**
     *初始化nacos config
     * @return
     */
    private ConfigService initConfigService() {

        try {
            Properties properties = new Properties();
            properties.setProperty("serverAddr",GatewayConfig.NACOS_SERVER_ADDR);
            properties.setProperty("namespace",GatewayConfig.NACOS_NAMESPACE);
            return configService = NacosFactory.createConfigService(properties);
        } catch (Exception ex) {
            log.error("init gateway config error : [{}]",ex.getMessage(),ex);
            return null;
        }
    }

    /**
     * 监听nacos 下发的动态路由配置
     * @param dataId
     * @param group
     */
    private void dynamicRouteNacosListener(String dataId,String group) {

        try {

            //给Nacos Config客户端增加一个监听器
            configService.addListener(dataId, group, new Listener() {

                /**
                 * 自己提供线程池执行操作
                 * ，如果不提供则使用默认的线程池
                 * @return
                 */
                @Override
                public Executor getExecutor() {
                    return null;
                }

                /**
                 * 监听器收到配置更新
                 * @param s
                 */
                @Override
                public void receiveConfigInfo(String s) {

                    log.info("start to update config: [{}]",s);
                    List<RouteDefinition> definitionList = JSON.parseArray(s, RouteDefinition.class);
                    log.info("update route : [{}]",definitionList.toString());
                    //更新路由配置
                    dynamicRouteService.updateList(definitionList);
                }
            });
        }catch (NacosException ex) {
            log.error("dynamic update gateway config error : [{}],",ex.getMessage(),ex);
        }
    }

    /**
     * Bean在容器中构造完成之后会执行
     */
    @PostConstruct
    public void init() {

        log.info("gateway route init ...");

        try {
            //初始化Nacos配置客户端
            configService = initConfigService();
            if (null == configService) {
                log.error("init configService fail");
                return;
            }

            //通过Nacos config并指定路由配置去获取路由配置
            String configInfo = configService.getConfig(
                    GatewayConfig.NACOS_ROUTER_DATA_ID,
                    GatewayConfig.NACOS_ROUTER_GROUP,
                    GatewayConfig.DEFAULT_TIMEOUT
            );

            log.info("get current gateway config : [{}]",configInfo);
            List<RouteDefinition> definitionList = JSON.parseArray(configInfo, RouteDefinition.class);
            if (CollectionUtil.isNotEmpty(definitionList)) {
                for (RouteDefinition routeDefinition : definitionList) {
                    log.info("init gateway info : [{}]",routeDefinition.toString());
                    dynamicRouteService.addRouteDefinition(routeDefinition);
                }
            }
        } catch (Exception ex) {
            log.info("gateway config init has some error : [{}]",ex.getMessage(),ex);
        }
        //设置监听器
        dynamicRouteNacosListener(GatewayConfig.NACOS_ROUTER_DATA_ID,GatewayConfig.NACOS_ROUTER_GROUP);
    }
}
