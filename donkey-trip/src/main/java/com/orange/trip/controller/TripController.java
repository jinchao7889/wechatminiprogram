package com.orange.trip.controller;

import com.orange.share.page.PageInfo;
import com.orange.share.response.ResponseWrapper;
import com.orange.tavels.info.TravelsPageInfo;
import com.orange.trip.info.TripInfo;
import com.orange.trip.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 行程
 */
@RestController
@RequestMapping("/trip")
public class TripController {
    @Autowired
    TripService tripService;

    @PostMapping("/add")
    public ResponseWrapper addTrip(@RequestBody @Validated TripInfo tripInfo){
        return ResponseWrapper.markSuccess(tripService.addTrip(tripInfo));
    }

    @PostMapping("/get_page")
    public ResponseWrapper getPageTrip(@RequestBody @Validated PageInfo pageInfo){
        return ResponseWrapper.markSuccess(tripService.getTripByPage(pageInfo));
    }

    @GetMapping("/get_detail/{id}")
    public ResponseWrapper getDetailTrip(@PathVariable String id){
        return ResponseWrapper.markSuccess(tripService.getTripDetails(id));
    }
    @GetMapping("/get/{travelId}")
    public  ResponseWrapper getTrip(@PathVariable String travelId){
        return ResponseWrapper.markSuccess(tripService.getTrip(travelId));
    }
    @PostMapping("/get_owner_page")
    public ResponseWrapper getOwnerPageTrip(@RequestBody @Validated TravelsPageInfo pageInfo){
        return ResponseWrapper.markSuccess(tripService.getOwnerPage(pageInfo));
    }

    @GetMapping("/get_all_trip_detail/{tripId}")
    public  ResponseWrapper getTrip(@PathVariable Long tripId){
        return ResponseWrapper.markSuccess(tripService.getAllTripDetail(tripId));
    }
}
