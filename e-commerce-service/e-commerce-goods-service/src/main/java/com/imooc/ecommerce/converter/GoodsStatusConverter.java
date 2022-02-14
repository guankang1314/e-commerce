package com.imooc.ecommerce.converter;

import com.imooc.ecommerce.constant.GoodsStatus;

import javax.persistence.AttributeConverter;

/**
 * @author qingtian
 * @version 1.0
 * @description: GoodsStatus枚举类转换器
 * @date 2022/2/13 21:33
 */
public class GoodsStatusConverter implements AttributeConverter<GoodsStatus, Integer> {

    /**
     * 转换成数据表中的基本类型
     * @param goodsStatus
     * @return
     */
    @Override
    public Integer convertToDatabaseColumn(GoodsStatus goodsStatus) {
        return goodsStatus.getStatus();
    }

    /**
     * 还原为goodsStatus
     * @param integer
     * @return
     */
    @Override
    public GoodsStatus convertToEntityAttribute(Integer integer) {
        return GoodsStatus.of(integer);
    }
}
