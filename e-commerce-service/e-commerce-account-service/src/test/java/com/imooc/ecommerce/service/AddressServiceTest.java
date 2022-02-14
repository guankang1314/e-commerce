package com.imooc.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.account.AddressInfo;
import com.imooc.ecommerce.common.TableId;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

/**
 * @author qingtian
 * @version 1.0
 * @description: 用户地址相关服务功能测试
 * @date 2022/1/17 23:40
 */
@Slf4j
public class AddressServiceTest extends BaseTest{

    @Autowired
    private IAddressService addressService;

    /**
     * @description: 测试创建用户地址信息
     * @param:
     * @return: void
     */
    @Test
    public void testCreateAddressInfo() {

        AddressInfo.AddressItem addressItem = new AddressInfo.AddressItem();
        addressItem.setUsername("guankang");
        addressItem.setPhone("18252087488");
        addressItem.setProvince("上海市");
        addressItem.setCity("上海市");
        addressItem.setAddressDetail("陆家嘴");

        log.info("test create address info : [{}]", JSON.toJSONString(
                addressService.createAddressInfo(
                        new AddressInfo(loginUserInfo.getId(),
                                Collections.singletonList(addressItem))
                )
        ));
    }

    @Test
    public void testGetCurrentAddressInfo() {

        log.info("test get current usr info : [{}]",JSON.toJSONString(
                addressService.getCurrentAddressInfo()
        ));
    }

    @Test
    public void testGetAddressInfoById() {

        log.info("test get address info by id : [{}]",JSON.toJSONString(
                addressService.getAddressInfoById(10L)
        ));
    }

    @Test
    public void testGetAddressInfoByTableId() {
        log.info("test get address info by tableId : [{}]",JSON.toJSONString(
                addressService.getAddressInfoByIds(
                        new TableId(Collections.singletonList(new TableId.Id(10L)))
                )
        ));
    }
}
