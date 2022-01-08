package com.imooc.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author qingtian
 * @version 1.0
 * @description: 用户地址表实体
 * @date 2022/1/8 23:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_ecommerce_address")
public class EcommerceAddress {

    /**
     * @description: 自增主键
     * @date 2022/1/8 23:26
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    /**
     * @description: 用户id
     */
    @Column(name = "user_id",nullable = false)
    private Long userId;

    /**
     * @description: 用户名
     */
    @Column(name = "username",nullable = false)
    private String username;

    /**
     * @description: 电话号码
     */
    @Column(name = "phone",nullable = false)
    private String phone;

    /**
     * @description: 省份
     */
    @Column(name = "province",nullable = false)
    private String province;

    /**
     * @description: 市
     */
    @Column(name = "city",nullable = false)
    private String city;

    /**
     * @description: 详细地址
     */
    @Column(name = "address_detail",nullable = false)
    private String addressDetail;

    /**
     * @description: 创建时间
     */
    @CreatedDate
    @Column(name = "create_time",nullable = false)
    private Date createTime;

    /**
     * @description: 更新时间
     */
    @LastModifiedDate
    @Column(name = "update_time",nullable = false)
    private Date updateTime;
}
