package com.imooc.ecommerce.service.async;

import com.imooc.ecommerce.goods.GoodsInfo;

import java.util.List;

/**
 * @author qingtian
 * @version 1.0
 * @description: 异步服务接口定义
 * @date 2022/2/23 0:04
 */
public interface IAsyncService {


    /**
     * 异步将商品信息保存入库
     * @param goodsInfos
     * @param taskId
     */
    void asyncImportGoods(List<GoodsInfo> goodsInfos,String taskId);
}
