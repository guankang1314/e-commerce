package com.imooc.ecommerce.controller;

import com.imooc.ecommerce.account.BalanceInfo;
import com.imooc.ecommerce.service.IBalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author qingtian
 * @version 1.0
 * @description: 用户余额服务controller
 * @date 2022/2/6 14:42
 */
@Api(tags = "用户余额服务")
@Slf4j
@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private IBalanceService balanceService;

    @ApiOperation(value = "当前用户", notes = "获取当前用户余额信息", httpMethod = "GET")
    @GetMapping("/current-balance")
    public BalanceInfo getCurrentUserBalanceInfo() {
        return balanceService.getCurrentUserBalanceInfo();
    }

    @ApiOperation(value = "扣减",notes = "扣减用户余额",httpMethod = "PUT")
    @PutMapping("/deduct-balance")
    public BalanceInfo deductBalance(@RequestBody BalanceInfo balanceInfo) {
        return balanceService.deductBalance(balanceInfo);
    }
}
