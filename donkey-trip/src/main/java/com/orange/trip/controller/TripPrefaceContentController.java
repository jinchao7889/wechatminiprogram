package com.orange.trip.controller;

import com.orange.share.response.ResponseWrapper;
import com.orange.trip.domain.TripPrefaceContent;
import com.orange.trip.service.TripPrefaceContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trip_preface_content")
public class TripPrefaceContentController {
    @Autowired
    TripPrefaceContentService contentService;

    @PostMapping("/add")
    public ResponseWrapper add(@RequestBody TripPrefaceContent content){
        return ResponseWrapper.markSuccess(contentService.addTripPrefaceContent(content));
    }

    @GetMapping("/get/{id}")
    public ResponseWrapper get(@PathVariable Long id){
        return ResponseWrapper.markSuccess(contentService.findTripPrefaceContent(id));
    }
}
