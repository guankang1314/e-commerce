package com.imooc.ecommerce.dao;

import com.imooc.ecommerce.entity.EcommerceAddress;
import jdk.internal.net.http.LineSubscriberAdapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qingtian
 * @version 1.0
 * @description: EcommerceAddress Dao 接口
 * @date 2022/1/9 17:28
 */
@Repository
public interface EcommerceAddressDao extends JpaRepository<EcommerceAddress,Long> {

    /**
     * @description:  根据用户id查询地址信息
     * @param: id
     * @return: java.util.List<com.imooc.ecommerce.entity.EcommerceAddress>
     * @date: 2022/1/9 17:30
     */
    List<EcommerceAddress> findAllByUserId(Long id);
}
