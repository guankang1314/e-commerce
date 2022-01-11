package com.imooc.ecommerce.account;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author qingtian
 * @version 1.0
 * @description: TODO
 * @date 2022/1/9 7:41
 */
@ApiModel(description = "用户地址信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfo {

    @ApiModelProperty(value = "用户地址所属用户id")
    private Long userId;

    @ApiModelProperty(value = "地址详细信息")
    private List<AddressItem> addressItems;

    /**
     * @description: 单个地址信息
     */
    @ApiModel(description = "单个地址信息")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AddressItem {

        @ApiModelProperty(value = "地址表主键id")
        private Long id;

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

        @ApiModelProperty(value = "创建时间")
        private Date createTime;

        @ApiModelProperty(value = "更新时间")
        private Date updateTime;

        public AddressItem(Long id) {
            this.id = id;
        }

        /**
         * @description: 将 AddressItem 转化成 UserAddress
         */
        public UserAddress toUserAddress() {
            UserAddress userAddress = UserAddress.builder()
                    .username(this.username)
                    .phone(this.phone)
                    .province(this.province)
                    .city(this.city)
                    .addressDetail(this.addressDetail)
                    .build();
            return userAddress;
        }
    }
}
