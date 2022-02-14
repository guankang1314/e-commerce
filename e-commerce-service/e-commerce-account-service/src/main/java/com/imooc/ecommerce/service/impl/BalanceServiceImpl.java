package com.imooc.ecommerce.service.impl;

import com.imooc.ecommerce.account.BalanceInfo;
import com.imooc.ecommerce.dao.EcommerceBalanceDao;
import com.imooc.ecommerce.entity.EcommerceBalance;
import com.imooc.ecommerce.filter.AccessContext;
import com.imooc.ecommerce.service.IBalanceService;
import com.imooc.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qingtian
 * @version 1.0
 * @description: 用户余额相关的服务接口实现
 * @date 2022/1/17 23:59
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BalanceServiceImpl implements IBalanceService {

    @Autowired
    private EcommerceBalanceDao balanceDao;

    @Override
    public BalanceInfo getCurrentUserBalanceInfo() {

        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        BalanceInfo balanceInfo = new BalanceInfo(loginUserInfo.getId(), 0L);
        EcommerceBalance ecommerceBalance = balanceDao.findByUserId(loginUserInfo.getId());

        if (null != ecommerceBalance) {
            balanceInfo.setBalance(ecommerceBalance.getBalance());
        } else {
            //如果还没有用户余额记录，创建记录
            EcommerceBalance newBalance = new EcommerceBalance();
            newBalance.setUserId(loginUserInfo.getId());
            newBalance.setBalance(0L);
            log.info("init user balance record : [{}]",balanceDao.save(newBalance).getId());
        }
        return balanceInfo;
    }

    @Override
    public BalanceInfo deductBalance(BalanceInfo balanceInfo) {

        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        //扣减用户余额
        EcommerceBalance ecommerceBalance = balanceDao.findByUserId(loginUserInfo.getId());
        if (null == ecommerceBalance
                || ecommerceBalance.getBalance() - balanceInfo.getBalance() < 0) {
            throw new RuntimeException("user balance is not enough");
        }

        Long sourceBalance = ecommerceBalance.getBalance();
        ecommerceBalance.setBalance(ecommerceBalance.getBalance() - balanceInfo.getBalance());
        log.info("deduct balance : [{}],[{}],[{}]",
                balanceDao.save(ecommerceBalance).getId(),sourceBalance,
                balanceInfo.getBalance());

        return new BalanceInfo(ecommerceBalance.getUserId(),ecommerceBalance.getBalance());
    }
}
