package com.orange.activity.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 活动轮播图
 */
@Data
@Entity
@Table(name = "activity_carousel_img")
public class ActivityCarouselImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    /**
     * 活动ID
     */
    @Column(name = "activity_id")
    String activityId;
    @Column(name = "img_url")
    String imgUrl;
    /**
     * 序号，用于排序
     */
    @Column(name = "serial_number")
    Integer serialNumber;
}
