package com.imooc.ecommerce.feign;

import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.goods.DeductGoodsInventory;
import com.imooc.ecommerce.goods.SimpleGoodsInfo;
import com.imooc.ecommerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Guank
 * @version 1.0
 * @description: 不安全的商品服务接口
 * @date 2022/6/18 15:38
 */
@FeignClient(
        contextId = "NotSecuredGoodsClient",
        value = "e-commerce-goods-service"
)
public interface NotSecuredGoodsClient {

    /**
     * 扣减库存
     * @param deductGoodsInventories
     * @return
     */
    @RequestMapping(
            value = "/e-commerce-goods-service/goods/deduct-goods-inventory",
            method = RequestMethod.PUT
    )
    CommonResponse<Boolean> deductGoodsInventory(@RequestBody List<DeductGoodsInventory> deductGoodsInventories);


    /**
     * 根据 ids 查询简单的商品信息
     * @param tableId
     * @return
     */
    @RequestMapping(
            value = "/e-commerce-goods-service/goods/simple-goods-info",
            method = RequestMethod.POST
    )
    CommonResponse<List<SimpleGoodsInfo>> getSimpleGoodsInfoByTableId(@RequestBody TableId tableId);
}
