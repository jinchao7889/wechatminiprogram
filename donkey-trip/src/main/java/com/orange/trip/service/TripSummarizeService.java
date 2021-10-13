package com.orange.trip.service;

import com.orange.share.vo.PageVo;
import com.orange.trip.domain.TripSummarize;
import com.orange.trip.dto.TripSummarizeDto;
import com.orange.trip.info.SummarizePageInfo;
import com.orange.trip.info.TripSummarizeInfo;
import com.orange.trip.vo.TripSummarizeVo;

import java.util.List;

public interface TripSummarizeService {
    TripSummarize addTripSummarize(TripSummarizeInfo summarizeInfo);

    List<TripSummarizeVo> addManyTripSummarize(List<TripSummarizeDto> summarizeDtos);

    void delTripSummarize(Integer id);

    PageVo getTripSummarizeByPage(SummarizePageInfo pageInfo);

    List<TripSummarizeVo> getALL(Long tripId);
}
