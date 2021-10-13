package com.orange.trip.controller;


import com.orange.share.constant.ReturnCode;
import com.orange.share.page.PageInfo;
import com.orange.share.response.ResponseWrapper;
import com.orange.trip.service.TripCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trip_collection")
public class TripCollectionController {
    @Autowired
    TripCollectionService collectionService;

    @GetMapping("/add/{ti}")
    public ResponseWrapper add(@PathVariable Long ti){
        return ResponseWrapper.markSuccess(collectionService.addTripCollection(ti));
    }

    @GetMapping("/cancel/{ti}")
    public ResponseWrapper cancel(@PathVariable Long ti){
        collectionService.cancelTripCollection(ti);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }

    @PostMapping("/get_owner_page")
    public ResponseWrapper getPage(@RequestBody PageInfo pageInfo){
        return ResponseWrapper.markSuccess(collectionService.getOwnerPage(pageInfo));
    }
}
