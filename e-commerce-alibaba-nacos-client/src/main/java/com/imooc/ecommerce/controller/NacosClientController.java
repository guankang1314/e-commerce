package com.imooc.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.imooc.ecommerce.service.NacosClientService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.ecommerce.controller
 * @date 2021/12/4 11:15
 */
@Slf4j
@RestController
@RequestMapping("/nacos-client")
public class NacosClientController {

    private final NacosClientService nacosClientService;

    public NacosClientController(NacosClientService nacosClientService) {
        this.nacosClientService = nacosClientService;
    }

    /**
     * 根据serviceId发现所有的实例信息
     * @param serviceId
     * @return
     */
    @GetMapping("/service-instance")
    public List<ServiceInstance> logNacosClientInfo(
            @RequestParam(defaultValue = "e-commerce-nacos-client") String serviceId) {

        log.info("coming in log nacos client info : [{}]",serviceId);
        return nacosClientService.getNacosClientInfo(serviceId);
    }
}
