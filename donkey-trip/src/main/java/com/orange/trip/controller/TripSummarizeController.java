package com.orange.trip.controller;

import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.trip.dto.TripSummarizeDto;
import com.orange.trip.info.SummarizePageInfo;
import com.orange.trip.info.TripSummarizeInfo;
import com.orange.trip.service.TripSummarizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 行程大概
 */
@RestController
@RequestMapping("/trip_summarize")
public class TripSummarizeController {
    @Autowired
    TripSummarizeService tripSummarizeService;

    @PostMapping("/add")
    public ResponseWrapper addTripSummarize(@RequestBody @Validated TripSummarizeInfo info){
        return ResponseWrapper.markSuccess(tripSummarizeService.addTripSummarize(info));
    }

    @PostMapping("/batch/add")
    public ResponseWrapper addTripSummarize(@RequestBody List<TripSummarizeDto> summarizeDtos){
        return ResponseWrapper.markSuccess(tripSummarizeService.addManyTripSummarize(summarizeDtos));
    }

    @GetMapping("/del/{id}")
    public ResponseWrapper delTripSummarize(@PathVariable Integer id){
        tripSummarizeService.delTripSummarize(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }

    @PostMapping("/get_page")
    public ResponseWrapper getTripSummarizeByPage(@RequestBody @Validated SummarizePageInfo pageInfo){
        return ResponseWrapper.markSuccess(tripSummarizeService.getTripSummarizeByPage(pageInfo));
    }

    @GetMapping("/get_all/{tripId}")
    public ResponseWrapper getTripSummarizeByPage(@PathVariable Long tripId){
        return ResponseWrapper.markSuccess(tripSummarizeService.getALL(tripId));
    }
}
