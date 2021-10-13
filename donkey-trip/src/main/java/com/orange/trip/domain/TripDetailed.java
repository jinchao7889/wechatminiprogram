package com.orange.trip.domain;


import lombok.Data;

import javax.persistence.*;


/**
 * 行程详细
 */
@Table(name = "trip_detailed")
@Entity
@Data
public class TripDetailed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * 行程大概的ID
     */
    @Column(name = "trip_summarize_id")
    Integer tripSummarizeId;

    /**
     * 内容
     */
    String content;

    /**
     * 排序
     */
    @Column(name = "serial_number")
    Integer serialNumber;

    /**
     * 大概时间
     */
    String summarizeTime;
}
