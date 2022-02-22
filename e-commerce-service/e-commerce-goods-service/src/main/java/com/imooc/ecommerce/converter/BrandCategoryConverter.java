package com.imooc.ecommerce.converter;

import com.imooc.ecommerce.constant.BrandCategory;

import javax.persistence.AttributeConverter;

/**
 * @author qingtian
 * @version 1.0
 * @description: 商品品牌枚举转换器
 * @date 2022/2/16 22:28
 */
public class BrandCategoryConverter implements AttributeConverter<BrandCategory,String> {

    @Override
    public String convertToDatabaseColumn(BrandCategory brandCategory) {
        return brandCategory.getCode();
    }

    @Override
    public BrandCategory convertToEntityAttribute(String code) {
        return BrandCategory.of(code);
    }
}
