package com.imooc.ecommerce.constant;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author qingtian
 * @version 1.0
 * @description: 商品状态枚举类
 * @date 2022/2/13 21:23
 */
@Getter
@AllArgsConstructor
public enum GoodsStatus {

    ONLINE(101,"上线"),
    OFFLINE(102,"下线"),
    STOCK_OUT(103,"缺货"),
    ;

    private final Integer status;

    private final String description;

    /**
     * 将枚举类转换成实体对象
     * @param status
     * @return
     */
    public static GoodsStatus of(@NotNull Integer status) {
        return Stream.of(values()).filter(bean -> bean.equals(status))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(status + "not exists"));
    }

}
