package com.imooc.ecommerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author qingtian
 * @version 1.0
 * @description: 商品类别
 * @date 2022/2/16 22:52
 */
@Getter
@AllArgsConstructor
public enum GoodsCategory {

    DIAN_QI("10001","电器"),
    JIA_JU("10002","家具"),
    FU_SHI("10003","服饰"),
    MY_YIN("10004","母婴"),
    SHI_PIN("10005","食品"),
    TU_SHU("10006","图书"),
    ;
    /**
     * 商品分类编码
     */
    private final String code;

    /**
     * 商品分类描述
     */
    private final String description;

    public static GoodsCategory of(String code) {

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException(code + "is not exist")
                );
    }
}
