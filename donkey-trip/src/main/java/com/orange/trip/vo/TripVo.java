package com.orange.trip.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TripVo {
    /**
     * 用户ID
     */
    Long tripId;
    String userId;
    /**
     * 作者头像
     */
    String userHead;
    /**
     * 作者昵称
     */
    String userNickname;

    Integer userGrade;

    /**
     * 文章标题
     */
    String title;
    /**
     * 发布时间
     */
    Long releaseTime;

    Long collectionVolume;

    /**
     * 旅行的天数
     */
    Double travelDays;

    /**
     * 旅行的方式
     */
    String travelType;

    Double perCapitaConsumption;

    String coverMap;
    String travelsId;

}
