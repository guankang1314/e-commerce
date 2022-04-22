package com.imooc.ecommerce.controller;

import com.imooc.ecommerce.service.communucation.AuthorityFeignClient;
import com.imooc.ecommerce.service.communucation.UseFeignApi;
import com.imooc.ecommerce.service.communucation.UseRestTemplateService;
import com.imooc.ecommerce.service.communucation.UseRibbonService;
import com.imooc.ecommerce.vo.JwtToken;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qingtian
 * @version 1.0
 * @description: 微服务通信controller
 * @date 2022/3/15 23:32
 */
@RestController
@Slf4j
@RequestMapping("/communication")
public class CommunicationController {

    @Autowired
    private UseRestTemplateService useRestTemplateService;

    @Autowired
    private UseRibbonService useRibbonService;

    @Autowired
    private AuthorityFeignClient authorityFeignClient;

    @Autowired
    private UseFeignApi useFeignApi;

    @PostMapping("/rest-template")
    public JwtToken getTokenFromAuthorityService(@RequestBody UsernameAndPassword usernameAndPassword) {
            return useRestTemplateService.getTokenFromAuthorityService(usernameAndPassword);
    }

    @PostMapping("/rest-template-load-balancer")
    public JwtToken getTokenFromAuthorityServiceWithLoadBalancer(@RequestBody UsernameAndPassword usernameAndPassword) {
        return useRestTemplateService.getTokenFromAuthorityServiceWithLoadBalancer(usernameAndPassword);
    }

    @PostMapping("/ribbon")
    public JwtToken getTokenFromAuthorityServiceByRibbon(@RequestBody UsernameAndPassword usernameAndPassword) {
        return useRibbonService.getTokenFromAuthorityServiceByRibbon(usernameAndPassword);
    }

    @PostMapping("/token-by-feign")
    public JwtToken getTokenByFeign(@RequestBody UsernameAndPassword usernameAndPassword) {
        return authorityFeignClient.getTokenByFeign(usernameAndPassword);
    }

    @PostMapping("/thinking-in-feign")
    public JwtToken thinkingInFeign(@RequestBody UsernameAndPassword usernameAndPassword) {
        return useFeignApi.thinkingInFeign(usernameAndPassword);
    }
}
