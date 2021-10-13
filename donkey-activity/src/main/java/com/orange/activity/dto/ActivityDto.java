package com.orange.activity.dto;

import com.orange.activity.domain.ActivityCarouselImg;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ActivityDto {
    String id;

    /**
     * 活动开始时间
     */
    Long startActivity;

    /**
     * 活动结束时间
     */
    Double activityDays;

    /**
     * 活动最大人数
     */
    Integer maxPeopleNumber;
    String title;

    BigDecimal earnestMoney;

    BigDecimal price;
    /**
     * 已报名人数
     */
    Integer enrolmentPeopleNumber;

    /**
     * 集合地点
     */
    String collectionPlace;


    Long  enrolmentEndTime;


    String coverMap;

    Integer activityStatus;

    String content;

    Long productId;

    List<ActivityCarouselImg> carouselImgs;
}
