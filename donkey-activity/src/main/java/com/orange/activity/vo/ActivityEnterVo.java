package com.orange.activity.vo;

import lombok.Data;

@Data
public class ActivityEnterVo {
    Long id;
    String activityId;
    String title;
    /**
     * 活动开始时间
     */
    Long startActivity;



    /**
     * 活动最大人数
     */
    Integer maxPeopleNumber;


    /**
     * 集合地点
     */
    String collectionPlace;



    Long createTime;

    String coverMap;


    /**
     * 付款状态
     * 1. 未付款
     * 2. 已付款
     * 3. 无需付款
     */
    Integer paymentStatus;
    Integer enterNumber=1;

}
