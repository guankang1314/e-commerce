package com.imooc.ecommerce.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qingtian
 * @version 1.0
 * @description: 主键 ids
 * @date 2022/1/14 23:27
 */
@ApiModel(description = "通用id对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableId {


    @ApiModelProperty(value = "数据表记录主键")
    private List<Id> ids;

    @ApiModel(description = "数据表记录主键对象")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id {

        @ApiModelProperty(value = "数据表记录主键")
        private Long id;
    }
}
