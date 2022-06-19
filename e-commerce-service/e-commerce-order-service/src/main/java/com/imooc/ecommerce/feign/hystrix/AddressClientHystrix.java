package com.imooc.ecommerce.feign.hystrix;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.account.AddressInfo;
import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.feign.AddressClient;
import com.imooc.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author Guank
 * @version 1.0
 * @description: 账户服务 fallback 策略
 * @date 2022/6/18 16:59
 */
@Slf4j
@Component
public class AddressClientHystrix implements AddressClient {
    @Override
    public CommonResponse<AddressInfo> getAddressInfoByTablesId(TableId tableId) {
        log.error("account client feign request error in order service get address info error : [{}]",
                JSON.toJSONString(tableId));
        return new CommonResponse<>(
                -1,
                "account client feign request error in order service",
                new AddressInfo(-1L, Collections.emptyList())
        );
    }
}
