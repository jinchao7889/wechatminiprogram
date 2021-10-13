package com.orange.activity.vo;

import lombok.Data;

@Data
public class ActivityEnterDetailVo {
    Long id;

    String activityId;

    String userId;

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

    /**
     * 活动标题
     */
    String activityTitle;

    /**
     * 付款状态
     * 1. 未付款
     * 2. 已付款
     * 3. 无需付款
     * 4. 失效
     */
    Integer paymentStatus;

    /**
     * 订单号
     */
    String orderId;

    Long productId;
    Integer enterNumber=1;

    /**
     * 活动状态
     */
    Integer activityStatus;

}
