package com.orange.trip.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;


@Data
@Document
public class TravelsPool {
    String id;
    String title;
    /**
     * 封面
     */
    String coverMap;

    String userId;


    /**
     * 发布时间
     */
    Long releaseTime;

    /**
     * 收藏数量
     */
    Integer collectionVolume=0;


    /**
     * 浏览量
     */
    Long browseVolume=0L;

    /**
     * 评论数量
     */
    Long commentVolume=0L;

    /**
     * 点赞数量
     */
    Long fabulousVolume=0L;

    /**
     * 出发时间
     */
    Long departureTime;

    /**
     * 旅行天数
     */
    Double travelDays=0.0;

    /**
     * 旅行的方式
     */
    String travelType;

    /**
     * 人均消费
     */
    Double perCapitaConsumption;

    String  travelDestination;

    /**
     * 游记内容
     */
    String travelContent;

    Boolean enable=true;
    Long tripId;

    String tripCoverMap;
}
