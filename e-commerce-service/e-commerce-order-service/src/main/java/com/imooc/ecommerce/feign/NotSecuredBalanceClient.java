package com.imooc.ecommerce.feign;

import com.imooc.ecommerce.account.BalanceInfo;
import com.imooc.ecommerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Guank
 * @version 1.0
 * @description: 不安全的 feign 接口定义
 * @date 2022/6/17 20:30
 */
@FeignClient(
        contextId = "NotSecuredBalanceClient",
        value = "e-commerce-account-service"
)
public interface NotSecuredBalanceClient {

    @RequestMapping(value = "/ecommerce-account-service/balance/deduct-balance",
    method = RequestMethod.PUT)
    CommonResponse<BalanceInfo> deductBalance(@RequestBody BalanceInfo balanceInfo);
}
