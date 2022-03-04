package com.imooc.ecommerce.service;

import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.goods.DeductGoodsInventory;
import com.imooc.ecommerce.goods.GoodsInfo;
import com.imooc.ecommerce.goods.SimpleGoodsInfo;
import com.imooc.ecommerce.vo.PageSimpleGoodsInfo;

import java.util.List;

/**
 * @author qingtian
 * @version 1.0
 * @description: 商品模块接口
 * @date 2022/2/22 23:57
 */
public interface IGoodsService {

    /**
     * 根据 tableId 查询商品详细信息
     * @param tableId
     * @return
     */
    List<GoodsInfo> getGoodsInfoByTableId(TableId tableId);

    /**
     * 获取分页的商品信息
     * @param page
     * @return
     */
    PageSimpleGoodsInfo getSimpleGoodsInfoByPage(int page);

    /**
     * 根据 tableId 查询简单商品信息
     * @param tableId
     * @return
     */
    List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId);

    /**
     * 扣减商品库存
     * @param deductGoodsInventories
     * @return
     */
    Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories);

}
