package com.imooc.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qingtian
 * @version 1.0
 * @description: 消息的传递对象 ：SpringCloud Stream + Kafka / RocketMQ
 * @date 2022/5/3 9:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuankMessage {

    private Integer id;
    private String projectName;
    private String org;
    private String author;
    private String version;

    /**
     * 返回一个默认的消息
     * @return
     */
    public static GuankMessage defaultMessage() {
        return new GuankMessage(1, "e-commerce-stream-client", "qingtianblog.com", "guank", "1.0");
    }
}
