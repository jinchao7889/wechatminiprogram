package com.orange.trip.domain;

import com.orange.trip.vo.TripExpensesToVo;
import com.orange.trip.vo.TripExpensesVo;
import com.orange.trip.vo.TripInventoryVo;
import com.orange.trip.vo.TripSummarizeVo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class TripPool {
    Long id;
    String travelsId;
    String coverMap;
    /**
     * 行程前言
     */
    String tripPrefaceContent;

    List<TripSummarizeVo> tripSummarizeVos;

    List<TripInventoryVo> tripInventoryVos;
    TripExpensesToVo tripExpensesToVo;
}
