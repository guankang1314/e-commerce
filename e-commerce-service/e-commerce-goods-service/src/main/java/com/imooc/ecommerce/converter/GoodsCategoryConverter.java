package com.imooc.ecommerce.converter;

import com.imooc.ecommerce.constant.GoodsCategory;

import javax.persistence.AttributeConverter;

/**
 * @author qingtian
 * @version 1.0
 * @description: 商品类别枚举转换器
 * @date 2022/2/16 22:58
 */
public class GoodsCategoryConverter implements AttributeConverter<GoodsCategory,String> {
    @Override
    public String convertToDatabaseColumn(GoodsCategory goodsCategory) {
        return goodsCategory.getCode();
    }

    @Override
    public GoodsCategory convertToEntityAttribute(String s) {
        return GoodsCategory.of(s);
    }
}
