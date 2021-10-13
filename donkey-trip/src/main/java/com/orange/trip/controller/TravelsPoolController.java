package com.orange.trip.controller;

import com.orange.share.page.PageInfo;
import com.orange.share.response.ResponseWrapper;
import com.orange.trip.service.TravelsPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travels_pool")
public class TravelsPoolController {
    @Autowired
    TravelsPoolService travelsPoolService;
    @GetMapping("/pass/{travelsId}")
    public ResponseWrapper passTravelsPool(@PathVariable String travelsId){
        return ResponseWrapper.markSuccess(travelsPoolService.addTravelsPool(travelsId));
    }

    @PostMapping("/get_page")
    public ResponseWrapper getPageTravelsPool(@RequestBody PageInfo pageInfo){
        return ResponseWrapper.markSuccess(travelsPoolService.getPageOfTravels(pageInfo));
    }

    @PostMapping("/trip/get_page")
    public ResponseWrapper getPageTrip(@RequestBody PageInfo pageInfo){
        return ResponseWrapper.markSuccess(travelsPoolService.getTripPage(pageInfo));
    }

    @PostMapping("/trip/owner/get_page")
    public ResponseWrapper getOwnerPageTrip(@RequestBody PageInfo pageInfo){
        return ResponseWrapper.markSuccess(travelsPoolService.getOwnerTripPage(pageInfo));
    }

    @GetMapping("/get/tripContent/{tripId}")
    public ResponseWrapper getTrip(@PathVariable Long tripId){
        return ResponseWrapper.markSuccess(travelsPoolService.getTrip(tripId));
    }

    @GetMapping("/get/travelsContent/{travelsId}")
    public ResponseWrapper getContent(@PathVariable String travelsId){
        return ResponseWrapper.markSuccess(travelsPoolService.getContent(travelsId));
    }

    @GetMapping("/get/getTripDetail/{travelsId}")
    public ResponseWrapper getTripDetail(@PathVariable String travelsId){
        return ResponseWrapper.markSuccess(travelsPoolService.getTripDetails(travelsId));
    }
}
