package com.imooc.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.ecommerce.entity.EcommerceUser;

/**
 * @author qingtian
 * @description:dao的接口定义
 * @Package com.imooc.ecommerce.dao
 * @date 2021/12/5 10:08
 */
public interface EcommerceUserDao extends JpaRepository<EcommerceUser,Long> {

    EcommerceUser findByUsername(String username);

    EcommerceUser findByUsernameAndPassword(String username,String password);
}
