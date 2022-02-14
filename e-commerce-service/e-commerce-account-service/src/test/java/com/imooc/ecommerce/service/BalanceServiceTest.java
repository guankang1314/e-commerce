package com.imooc.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.account.BalanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qingtian
 * @version 1.0
 * @description: 用户余额相关服务测试
 * @date 2022/1/30 17:43
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class BalanceServiceTest extends BaseTest {

    @Autowired
    private IBalanceService balanceService;

    @Test
    public void testGetCurrentUsrBalanceInfo() {

        log.info("test get current user balance info : [{}]", JSON.toJSONString(
                balanceService.getCurrentUserBalanceInfo()
        ));
    }

    @Test
    public void testDeductBalance() {

        BalanceInfo balanceInfo = new BalanceInfo();
        balanceInfo.setUserId(loginUserInfo.getId());
        balanceInfo.setBalance(1000L);

        log.info("test deduct balance: [{}]",JSON.toJSONString(
                balanceService.deductBalance(balanceInfo)
        ));
    }
}
