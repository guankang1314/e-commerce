package com.imooc.ecommerce.controller;

import com.imooc.ecommerce.account.AddressInfo;
import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.service.IAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author qingtian
 * @version 1.0
 * @description: 用户地址服务controller
 * @date 2022/1/30 18:09
 */
@Api(tags = "用户地址服务")
@RestController
@RequestMapping("/address")
@Slf4j
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @ApiOperation(value = "创建",notes = "创建用户地址",httpMethod = "POST")
    @PostMapping("/create-address")
    public TableId createAddressInfo(@RequestBody AddressInfo addressInfo) {
        return addressService.createAddressInfo(addressInfo);
    }

    @ApiOperation(value = "当前用户",notes = "获取当前用户地址信息",httpMethod = "GET")
    @GetMapping("/current-address")
    public AddressInfo getCurrentAddressInfo() {
        return addressService.getCurrentAddressInfo();
    }

    @ApiOperation(value = "获取用户地址信息",notes = "通过用户id获取用户的地址信息",httpMethod = "GET")
    @GetMapping("/address-info")
    public AddressInfo getAddressInfoById(@RequestParam Long id) {
        return addressService.getAddressInfoById(id);
    }

    @ApiOperation(value = "获取用户地址信息",notes = "通过tableId获取用户地址信息",httpMethod = "POST")
    @PostMapping("/addressInfo-by-tableId")
    public AddressInfo getAddressInfoByTableId(@RequestBody TableId tableId) {
        return addressService.getAddressInfoByIds(tableId);
    }
}
