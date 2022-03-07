package com.imooc.ecommerce.converter;

import com.imooc.ecommerce.constant.BrandCategory;

import javax.persistence.AttributeConverter;

/**
 * <h1>品牌分类枚举属性转换器</h1>
 * */
public class BrandCategoryConverter implements AttributeConverter<BrandCategory, String> {

    @Override
    public String convertToDatabaseColumn(BrandCategory brandCategory) {
        if (null == brandCategory)
            return null;
        return brandCategory.getCode();
    }

    @Override
    public BrandCategory convertToEntityAttribute(String code) {
        if (null == code)
            return null;
        return BrandCategory.of(code);
    }
}
