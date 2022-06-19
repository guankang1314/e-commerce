package com.imooc.ecommerce.feign.hystrix;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.feign.SecuredGoodsClient;
import com.imooc.ecommerce.goods.SimpleGoodsInfo;
import com.imooc.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import java.util.Collections;
import java.util.List;

/**
 * @author Guank
 * @version 1.0
 * @description: 商品微服务 fallback 策略
 * @date 2022/6/18 16:55
 */
@Slf4j
@Component
public class GoodsClientHystrix implements SecuredGoodsClient {

    @Override
    public CommonResponse<List<SimpleGoodsInfo>> getSimpleGoodsInfoByTableId(TableId tableId) {
        log.error("goods client feign request error in order service get simple goods error : [{}]",
                JSON.toJSONString(tableId));
        return new CommonResponse<>(
                -1,
                "goods client feign request error in order service",
                Collections.emptyList()
        );
    }
}
