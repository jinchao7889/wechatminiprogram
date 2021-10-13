package com.orange.tavels.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TravelsContentVo {
    String title;

    /**
     * 封面
     */
    String coverMap;

    /**
     * 头像
     */
    String head;

    /**
     * 昵称
     */
    String nickname;

    /**
     * 浏览量
     */
    Long browseVolume;

    /**
     * 发布时间
     */
    Long releaseTime;



    /**
     * 是否点赞
     */
    Boolean isFabulous;

    String travelsId;

    String content;

    /**
     * 评论数量
     */
    Long commentVolume;

    /**
     * 点赞数量
     */
    Long fabulousVolume;

    /**
     * 出发时间
     */
    Long departureTime;

    /**
     * 旅行天数
     */
    Double travelDays=0.0;
    Long createTime;
    /**
     * 旅行的方式
     */
    String travelType;
    Boolean isFollow;
    String userId;
    Double perCapitaConsumption;
    String  travelDestination;
    Integer travelsStatus;

    Boolean isOwner;
}
