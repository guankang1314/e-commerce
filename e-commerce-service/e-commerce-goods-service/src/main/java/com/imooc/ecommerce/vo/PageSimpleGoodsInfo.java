package com.imooc.ecommerce.vo;

import com.imooc.ecommerce.goods.SimpleGoodsInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.message.LeaderAndIsrRequestData;

import java.util.List;

/**
 * @author qingtian
 * @version 1.0
 * @description: 分页商品信息
 * @date 2022/2/22 23:51
 */
@ApiModel(description = "分页商品信息对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageSimpleGoodsInfo {

    @ApiModelProperty(value = "分页简单商品信息")
    private List<SimpleGoodsInfo> simpleGoodsInfos;

    @ApiModelProperty(value = "是否有更多的商品信息")
    private Boolean hasMore;
}
