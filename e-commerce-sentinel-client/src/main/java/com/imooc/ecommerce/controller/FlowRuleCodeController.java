package com.imooc.ecommerce.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.block_handler.GuankBlockHandler;
import com.imooc.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.GTENegativeOne;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Guank
 * @version 1.0
 * @description: 限流规则硬编码的 controller
 * @date 2022-07-17 15:09
 */
@Slf4j
@RestController
@RequestMapping("/code")
public class FlowRuleCodeController {

    /**
     * 初始化流控规则
     */
    @PostConstruct
    public void init() {

        //流控规则集合
        List<FlowRule> flowRules = new ArrayList<>();
        //创建流控规则
        FlowRule flowRule = new FlowRule();
        //设置流控规则 QPS ，限流阈值类型(QPS,并发线程数)
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //流量控制手段 默认是直接抛弃
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        //设置受保护的资源
        flowRule.setResource("flowRuleCode");
        //设置受保护的资源的阈值 QPS = 1
        flowRule.setCount(1);
        flowRules.add(flowRule);

        //加载配置好的流控规则
        FlowRuleManager.loadRules(flowRules);
    }
    /**
     * 使用硬编码的限流规则
     * @return
     */
    @GetMapping("/flow-rule")
//    @SentinelResource(value = "flowRuleCode")
//    @SentinelResource(value = "flowRuleCode",blockHandler = "handleException")
    @SentinelResource(value = "flowRuleCode", blockHandler = "guankHandlerBlockException", blockHandlerClass = {GuankBlockHandler.class})
    public CommonResponse<String> flowRuleCode() {
        log.info("request flowRuleCode");
        return new CommonResponse<>(0,"","imooc-guank-commerce");
    }

    /**
     * 当限流异常抛出时，指定调用的方法 兜底策略
     * @param exception
     * @return
     */
    public CommonResponse<String> handleException(BlockException exception) {
        log.error("has block exception : [{}]", JSON.toJSONString(exception.getRule()),exception);
        return new CommonResponse<>(-1,
                "flow rule exception",
                exception.getClass().getCanonicalName());
    }
}
