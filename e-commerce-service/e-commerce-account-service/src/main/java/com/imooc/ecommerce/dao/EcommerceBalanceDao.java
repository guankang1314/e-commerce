package com.imooc.ecommerce.dao;

import com.imooc.ecommerce.entity.EcommerceBalance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author qingtian
 * @version 1.0
 * @description: TODO
 * @date 2022/1/11 23:17
 */
public interface EcommerceBalanceDao extends JpaRepository<EcommerceBalance,Long> {

    /**
     * @description:  根据 userId查询 EcommerceBalance对象
     * @param: id
     * @return: com.imooc.ecommerce.entity.EcommerceBalance
     * @date: 2022/1/11 23:18
     */
    EcommerceBalance findByUserId(Long id);
}
