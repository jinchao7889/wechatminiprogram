package com.orange.trip.info;

import lombok.Data;

@Data
public class TripDetailedInfo {
    /**
     * 行程大概的ID
     */
    Integer tripSummarizeId;

    /**
     * 内容
     */
    String content;

    /**
     * 排序
     */
    Integer serialNumber;
}
