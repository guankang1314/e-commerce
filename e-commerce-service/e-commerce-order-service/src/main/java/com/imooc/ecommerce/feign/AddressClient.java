package com.imooc.ecommerce.feign;

import com.imooc.ecommerce.account.AddressInfo;
import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.feign.hystrix.AddressClientHystrix;
import com.imooc.ecommerce.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Guank
 * @version 1.0
 * @description: 用户账户微服务 接口
 * @date 2022/6/18 16:48
 */
@FeignClient(
        contextId = "AddressClient",
        value = "e-commerce-account-service",
        fallback = AddressClientHystrix.class
)
public interface AddressClient {

    /**
     * 根据 id 查询地址信息
     * @param tableId
     * @return
     */
    @RequestMapping(
            value = "/ecommerce-account-service/address/addressInfo-by-tableId",
            method = RequestMethod.POST
    )
    CommonResponse<AddressInfo> getAddressInfoByTablesId(@RequestBody TableId tableId);
}
