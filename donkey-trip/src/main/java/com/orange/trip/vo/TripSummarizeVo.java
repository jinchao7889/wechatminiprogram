package com.orange.trip.vo;

import com.orange.trip.domain.TripDetailed;
import lombok.Data;

import java.util.List;

@Data
public class TripSummarizeVo {
    Integer id;
    Long tripId;
    String content;

    Integer serialNumber;

    /**
     * 更正信息
     */
    String correctionsContent;

    /**
     * 评价数量
     */
    Integer evaluateNumber=0;

    Boolean enable=true;
    String summarizeTime;
    List<TripDetailed> tripDetail;

    String tripTime;
}
