package com.imooc.ecommerce.converter;

import com.imooc.ecommerce.constant.GoodsCategory;

import javax.persistence.AttributeConverter;

/**
 * <h1>商品类别枚举属性转换器</h1>
 * */
public class GoodsCategoryConverter implements AttributeConverter<GoodsCategory, String> {

    @Override
    public String convertToDatabaseColumn(GoodsCategory goodsCategory) {
        if (null == goodsCategory)
            return null;
        return goodsCategory.getCode();
    }

    @Override
    public GoodsCategory convertToEntityAttribute(String code) {
        if (null == code)
            return null;
        return GoodsCategory.of(code);
    }
}
