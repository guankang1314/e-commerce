package com.imooc.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author qingtian
 * @description:配置类，读取Nacos中的配置项，用于配置监听器
 * @Package com.imooc.ecommerce.config
 * @date 2021/12/7 0:41
 */
@Configuration
public class GatewayConfig {

    /**
     * 读取配置的超时时间
     */
    public static final long DEFAULT_TIMEOUT = 30000;

    /**
     * Nacos服务器地址
     */
    public static String NACOS_SERVER_ADDR;

    /**
     * 命名空间
     */
    public static String NACOS_NAMESPACE;

    /**
     * data_id
     */
    public static String NACOS_ROUTER_DATA_ID;

    /**
     * 分组id
     */
    public static String NACOS_ROUTER_GROUP;


    @Value("${spring.cloud.nacos.discovery.server-addr}")
    public void setNacosServerAddr(String nacosServerAddr) {
        NACOS_SERVER_ADDR = nacosServerAddr;
    }

    @Value("${spring.cloud.nacos.discovery.namespace}")
    public void setNacosNamespace(String nacosNamespace) {
        NACOS_NAMESPACE = nacosNamespace;
    }

    @Value("${nacos.gateway.route.config.data-id}")
    public void setNacosRouterDataId(String nacosRouterDataId) {
        NACOS_ROUTER_DATA_ID = nacosRouterDataId;
    }

    @Value("${nacos.gateway.route.config.group}")
    public void setNacosRouterGroup(String nacosRouterGroup) {
        NACOS_ROUTER_GROUP = nacosRouterGroup;
    }

}
