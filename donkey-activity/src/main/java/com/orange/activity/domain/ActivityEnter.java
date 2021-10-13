package com.orange.activity.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 用户报名表
 */
@Data
@Table(name = "activity_enter")
@Entity
public class ActivityEnter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "activity_id")
    String activityId;

    @Column(name = "user_id")
    String userId;

    @Column(name = "create_time")
    Long createTime;

    /**
     * 联系电话
     */
    String contactNumber;
    /**
     * 凉席人身份证号码
     */
    String contactId;

    String gender;
    /**
     * 真实姓名
     */
    String realName;
    /**
     * QQ号码
     */
    String qqNumber;
    /**
     * 微信号
     */
    String wxNumber;

    @Column(name = "order_id",length = 32)
    String orderId;

    Boolean enable=true;

    @Column(name = "product_id")
    Long productId;

    /**
     * 1. 未付款
     * 2. 已付款
     */
    Integer paymentStatus;

    /**
     *报名人数
     */
    @Column(name = "enter_number")
    Integer enterNumber=1;
}
