package com.orange.activity.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 驴行
 */
@Data
@Table(name = "tb_activity")
@Entity
public class Activity {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    String id;

    /**
     * 活动开始时间
     */
    @Column(name = "start_activity")
    Long startActivity;

    /**
     * 活动结束时间
     */
    @Column(name = "activity_days")
    Double activityDays;

    /**
     * 活动最大人数
     */
    @Column(name = "max_people_number")
    Integer maxPeopleNumber;

    @Column(name = "title")

    String title;
    /**
     * 定金
     */
    @Column(name = "earnest_money")
    BigDecimal earnestMoney;

    /**
     * 价格
     */
    BigDecimal price;
    /**
     * 已报名人数
     */
    @Column(name = "enrolment_people_number")
    Integer enrolmentPeopleNumber;

    /**
     * 集合地点
     */
    @Column(name = "collection_place")
    String collectionPlace;

    /**
     * 报名截止时间
     */
    @Column(name = "enrolment_end_time")
    Long  enrolmentEndTime;

    @Column(name = "create_time")
    Long createTime;

    @Column(name = "cover_map")
    String coverMap;

    @Column(name = "activity_status")
    Integer activityStatus;

    /**
     * 0 代表没有产品 无需提交订单
     */
    @Column(name = "product_id")
    Long productId;

    Boolean enable=true;
}
