package com.imooc.ecommerce.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qingtian
 * @version 1.0
 * @description: TODO
 * @date 2022/1/9 7:52
 */
@ApiModel(description = "用户地址信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddress {

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "详细地址")
    private String addressDetail;
}
