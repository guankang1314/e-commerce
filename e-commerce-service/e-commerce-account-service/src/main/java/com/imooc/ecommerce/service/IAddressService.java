package com.imooc.ecommerce.service;

import com.imooc.ecommerce.account.AddressInfo;
import com.imooc.ecommerce.common.TableId;

/**
 * @author qingtian
 * @version 1.0
 * @description: 用户地址相关服务接口
 * @date 2022/1/14 23:25
 */
public interface IAddressService {

    /**
     * @description:  创建用户地址信息
     * @param: addressInfo
     * @return: com.imooc.ecommerce.common.TableId
     * @date: 2022/1/14 23:36
     */
    TableId createAddressInfo(AddressInfo addressInfo);

    /**
     * @description:  获取当前登陆的用户地址信息
     * @param:
     * @return: com.imooc.ecommerce.account.AddressInfo
     * @date: 2022/1/14 23:37
     */
    AddressInfo getCurrentAddressInfo();


    /**
     * @description:  通过 id 获取用户地址信息， id 是 EcommerceAddress 表中的主键
     * @param: id
     * @return: com.imooc.ecommerce.account.AddressInfo
     */
    AddressInfo getAddressInfoById(Long id);

    /**
     * @description:  通过 tableId 获取用户地址信息
     * @param: ids
     * @return: com.imooc.ecommerce.account.AddressInfo
     */
    AddressInfo getAddressInfoByIds(TableId ids);
}
