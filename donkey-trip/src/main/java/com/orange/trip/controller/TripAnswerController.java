package com.orange.trip.controller;

import com.orange.share.response.ResponseWrapper;
import com.orange.trip.info.AnswerInfo;
import com.orange.trip.info.AnswerPageInfo;
import com.orange.trip.service.TripAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 行程问答
 */
@RestController
@RequestMapping("/trip_answer")
public class TripAnswerController {
    @Autowired
    TripAnswerService tripAnswerService;

    @PostMapping("/add")
    public ResponseWrapper addAnswer(@Validated @RequestBody AnswerInfo answerInfo){
        return ResponseWrapper.markSuccess(tripAnswerService.addAnswer(answerInfo));
    }

    @PostMapping("/get_page")
    public ResponseWrapper getPage(@Validated @RequestBody AnswerPageInfo pageInfo){
        return ResponseWrapper.markSuccess(tripAnswerService.getAnswerPage(pageInfo));
    }
}