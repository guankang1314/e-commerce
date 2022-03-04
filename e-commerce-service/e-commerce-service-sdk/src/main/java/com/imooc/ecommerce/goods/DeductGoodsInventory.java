package com.imooc.ecommerce.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qingtian
 * @version 1.0
 * @description: 扣减商品库存
 * @date 2022/2/22 23:55
 */
@ApiModel(description = "扣减商品库存对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductGoodsInventory {

    @ApiModelProperty(value = "商品主键id")
    private Long goodsId;

    @ApiModelProperty(value = "扣减个数")
    private Integer count;
}
