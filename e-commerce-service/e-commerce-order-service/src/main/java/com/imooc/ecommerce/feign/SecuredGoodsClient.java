package com.imooc.ecommerce.feign;

import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.feign.hystrix.GoodsClientHystrix;
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
 * @description: 安全的商品服务接口定义
 * @date 2022/6/18 16:43
 */
@FeignClient(
        contextId = "SecuredGoodsClient",
        value = "e-commerce-goods-service",
        fallback = GoodsClientHystrix.class
)
public interface SecuredGoodsClient {

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
