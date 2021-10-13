package com.orange.trip.controller;

import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.trip.domain.TripQuestions;
import com.orange.trip.info.TripQuestionOwnerPageInfo;
import com.orange.trip.info.TripQuestionPageInfo;
import com.orange.trip.info.TripQuestionsInfo;
import com.orange.trip.service.TripQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 行程问题
 */
@RestController
@RequestMapping("/trip_questions")
public class TripQuestionsController {
    @Autowired
    TripQuestionsService tripQuestionsService;

    @PostMapping("/add")
    public ResponseWrapper addQuestion(@RequestBody @Validated TripQuestionsInfo questionsInfo){
        return ResponseWrapper.markSuccess(tripQuestionsService.addQuestion(questionsInfo));
    }

    @PostMapping("/get_page")
    public ResponseWrapper getQuestion(@RequestBody @Validated TripQuestionPageInfo pageInfo){
        return ResponseWrapper.markSuccess(tripQuestionsService.getQuestion(pageInfo));
    }

    @PostMapping("/owner/get_page")
    public ResponseWrapper getOwnerQuestion(@RequestBody @Validated TripQuestionPageInfo pageInfo){
        return ResponseWrapper.markSuccess(tripQuestionsService.getOwnerQuestion(pageInfo));
    }

    @GetMapping("/del/{questionsId}")
    public ResponseWrapper delQuestion(@PathVariable Integer questionsId){
        tripQuestionsService.delQuestion(questionsId);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }
}
