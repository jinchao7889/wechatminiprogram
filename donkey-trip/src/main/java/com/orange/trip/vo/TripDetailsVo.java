package com.orange.trip.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TripDetailsVo {
    /**
     * 是否关注
     */
    Boolean isFollow;

    /**
     * 是否收藏
     */
    Boolean isCollection;

    Long collectionVolume;
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
     * 旅行的天数
     */
    Double travelDays;

    /**
     * 旅行的方式
     */
    String travelType;


    String travelsId;
    String title;

    Boolean isOwner;
}
