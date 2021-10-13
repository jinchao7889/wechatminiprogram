package com.orange.tavels.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 游记
 */
@Entity
@Table(name = "tb_travels")
@Data
@JsonIgnoreProperties({"userId"})
public class Travels implements Serializable {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    String id;

    /**
     * 标题
     */

    String title;

    /**
     * 封面
     */
    String coverMap;


    @Column(name = "user_id")
    String userId;


    /**
     * 游记创建时间
     */
    @Column(name = "create_time")
    Long createTime;

    /**
     * 发布时间
     */
    @Column(name = "release_time")
    Long releaseTime;

    /**
     * 收藏数量
     */
    @Column(name = "collection_volume")
    Integer collectionVolume=0;


    Boolean enable=true;

    @Column(name = "travels_status")
    Integer travelsStatus;

    /**
     * 浏览量
     */
    @Column(name = "browse_volume")
    Long browseVolume=0L;

    /**
     * 评论数量
     */
    @Column(name = "comment_volume")
    Long commentVolume=0L;

    /**
     * 点赞数量
     */
    @Column(name = "fabulous_volume")
    Long fabulousVolume=0L;

    /**
     * 出发时间
     */
    @Column(name = "departure_time")
    Long departureTime;

    /**
     * 旅行天数
     */
    @Column(name = "travel_days")
    Double travelDays=0.0;

    /**
     * 旅行的方式
     */
    @Column(name = "travel_type")
    String travelType;

    /**
     * 人均消费
     */
    @Column(name = "per_capita_consumption")
    BigDecimal perCapitaConsumption;

    @Column(name = "travel_destination")
     String  travelDestination;
}
