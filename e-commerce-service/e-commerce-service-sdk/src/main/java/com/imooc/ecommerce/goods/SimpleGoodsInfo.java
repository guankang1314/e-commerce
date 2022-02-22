package com.imooc.ecommerce.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qingtian
 * @version 1.0
 * @description: 简单商品信息
 * @date 2022/2/19 16:34
 */
@ApiModel(description = "简单商品信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleGoodsInfo {

    @ApiModelProperty(value = "商品表主键 id")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品图片")
    private String goodsPic;

    @ApiModelProperty(value = "商品价格，单位：分")
    private Integer price;
}
