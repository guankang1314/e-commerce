package com.imooc.ecommerce.service;

import com.imooc.ecommerce.account.BalanceInfo;

/**
 * @author qingtian
 * @version 1.0
 * @description: 用户余额相关的服务接口定义
 * @date 2022/1/14 23:45
 */
public interface IBalanceService {

    /**
     * @description: 获取当前用户的余额信息
     * @param:
     * @return: com.imooc.ecommerce.account.BalanceInfo
     */
    BalanceInfo getCurrentUserBalanceInfo();

    /**
     *  扣减用户余额
     */
    BalanceInfo deductBalance(BalanceInfo balanceInfo);
}
