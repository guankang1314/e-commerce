package com.imooc.ecommerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author qingtian
 * @version 1.0
 * @description: 品牌分类枚举
 * @date 2022/2/13 21:37
 */
@Getter
@AllArgsConstructor
public enum BrandCategory {

    BRAND_A("20001","品牌 A"),
    BRAND_B("20002","品牌 B"),
    BRAND_C("20003","品牌 C"),
    BRAND_D("20004","品牌 D"),
    BRAND_E("20005","品牌 E"),
    ;
    /**
     * 品牌分类编码
     */
    private final String code;

    /**
     * 品牌分类描述
     */
    private final String description;

    /**
     * 根据 code 获取到 BrandCategory
     * @param code
     * @return
     */
    public static BrandCategory of(String code) {
        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException(code + "is not exist")
                );
    }
}
