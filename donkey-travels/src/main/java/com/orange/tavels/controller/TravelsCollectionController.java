package com.orange.tavels.controller;


import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.tavels.service.TravelsCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travels_collection")
public class TravelsCollectionController {
    @Autowired
    TravelsCollectionService collectionService;

    @GetMapping("/add/{travelsId}")
    public ResponseWrapper addTravelsCollection(@PathVariable String travelsId){

        return ResponseWrapper.markSuccess(collectionService.addTravelsCollection(travelsId));
    }
    @GetMapping("/cancel/{travelsId}")
    public ResponseWrapper cancelTravelsCollection(@PathVariable String travelsId){
        collectionService.cancelTravelsCollection(travelsId);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }
}
