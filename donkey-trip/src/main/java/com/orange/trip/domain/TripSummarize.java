package com.orange.trip.domain;


import com.orange.trip.vo.TripSummarizeVo;
import lombok.Data;

import javax.persistence.*;

/**
 * 行程总概
 */
@Table(name = "trip_summarize")
@Entity
@Data
public class TripSummarize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name = "trip_id")
    Long tripId;
    String content;

    @Column(name = "serial_number")
    Integer serialNumber;

    /**
     * 更正信息
     */
    @Column(name = "corrections_content")
    String correctionsContent;

    /**
     * 评价数量
     */
    Integer evaluateNumber=0;

    /**
     * 大概时间
     */
    String summarizeTime;
    Boolean enable=true;
    String tripTime;

}
