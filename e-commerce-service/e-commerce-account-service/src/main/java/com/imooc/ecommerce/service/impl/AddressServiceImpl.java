package com.imooc.ecommerce.service.impl;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.account.AddressInfo;
import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.dao.EcommerceAddressDao;
import com.imooc.ecommerce.entity.EcommerceAddress;
import com.imooc.ecommerce.filter.AccessContext;
import com.imooc.ecommerce.service.IAddressService;
import com.imooc.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qingtian
 * @version 1.0
 * @description: 用户地址相关服务的接口实现
 * @date 2022/1/16 18:52
 */
@Service
@Slf4j
@Transactional
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private EcommerceAddressDao addressDao;



    /**
     * @description: 储存多个地址信息
     * @param: addressInfo
     * @return: com.imooc.ecommerce.common.TableId
     */
    @Override
    public TableId createAddressInfo(AddressInfo addressInfo) {

        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        List<EcommerceAddress> ecommerceAddresses = addressInfo.getAddressItems().stream()
                .map(a -> EcommerceAddress.to(loginUserInfo.getId(), a)).collect(Collectors.toList());

        //保存到数据表 把返回的记录给出去
        List<EcommerceAddress> savedRecords = addressDao.saveAll(ecommerceAddresses);
        List<Long> ids = savedRecords.stream().map(EcommerceAddress::getId).collect(Collectors.toList());
        log.info("create address info : [{}],[{}]",loginUserInfo.getId(),
                JSON.toJSONString(ids));
        return new TableId(
                ids.stream().map(TableId.Id::new).collect(Collectors.toList())
        );
    }

    @Override
    public AddressInfo getCurrentAddressInfo() {

        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        //根据 userId 查询地址信息，在实现转换
        List<EcommerceAddress> ecommerceAddresses = addressDao.findAllByUserId(loginUserInfo.getId());
        List<AddressInfo.AddressItem> addressItems = ecommerceAddresses.stream()
                .map(EcommerceAddress::toAddressItem).collect(Collectors.toList());

        return new AddressInfo(loginUserInfo.getId(),addressItems);
    }

    @Override
    public AddressInfo getAddressInfoById(Long id) {

        //根据主键查询
        EcommerceAddress ecommerceAddress = addressDao.findById(id).orElse(null);
        if (null == ecommerceAddress) {
            throw new RuntimeException("address is not exist");
        }

        return new AddressInfo(ecommerceAddress.getUserId(),
                Collections.singletonList(ecommerceAddress.toAddressItem()));
    }

    /**
     * @description: 根据 tableId 查询地址信息
     * @param: ids
     * @return: com.imooc.ecommerce.account.AddressInfo
     */
    @Override
    public AddressInfo getAddressInfoByIds(TableId ids) {

        List<Long> idList = ids.getIds().stream()
                .map(TableId.Id::getId).collect(Collectors.toList());
        log.info("get address info by table id : [{}]",JSON.toJSONString(idList));

        List<EcommerceAddress> ecommerceAddresses = addressDao.findAllById(idList);
        if (CollectionUtils.isEmpty(ecommerceAddresses)) {
            return new AddressInfo(-1L,Collections.emptyList());
        }

        List<AddressInfo.AddressItem> addressItems = ecommerceAddresses.stream()
                .map(EcommerceAddress::toAddressItem)
                .collect(Collectors.toList());
        return new AddressInfo(ecommerceAddresses.get(0).getUserId(),addressItems);
    }
}
