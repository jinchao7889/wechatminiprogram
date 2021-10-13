package com.orange.trip.controller;

import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.trip.info.TripDetailedInfo;
import com.orange.trip.service.TripDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 行程详细
 */
@RestController
@RequestMapping("/trip_detailed")
public class TripDetailedController {
    @Autowired
    TripDetailedService tripDetailedService;

    @PostMapping("/add")
    public ResponseWrapper addTrip(@RequestBody @Validated TripDetailedInfo detailedInfo){
        return ResponseWrapper.markSuccess(tripDetailedService.addTripDetailed(detailedInfo));
    }

    @GetMapping("/del/{id}")
    public ResponseWrapper delTripDetailed(@PathVariable Integer id){
        tripDetailedService.delTripDetailed(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS.getMsg());
    }

    @GetMapping("/get_all/{tripSummarizeId}")
    public ResponseWrapper getAllTripDetailed(@PathVariable Integer tripSummarizeId){
        return ResponseWrapper.markSuccess(tripDetailedService.getAllTripDetailed(tripSummarizeId));
    }
}
