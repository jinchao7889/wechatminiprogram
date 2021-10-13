package com.orange.tavels.controller;


import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.tavels.domain.TravelsFabulous;
import com.orange.tavels.service.TravelsFabulousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travels_comment_fabulous")
public class TravelsFabulousController {
    @Autowired
    TravelsFabulousService travelsFabulousService;

    @GetMapping("/add/{commentId}")
    public ResponseWrapper add(@PathVariable Integer commentId){
        return ResponseWrapper.markSuccess(travelsFabulousService.addFabulous(commentId));
    }

    @GetMapping("/cancel/{commentId}")
    public ResponseWrapper cancel(@PathVariable Integer commentId){
        travelsFabulousService.cancelFabulous(commentId);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }
}
