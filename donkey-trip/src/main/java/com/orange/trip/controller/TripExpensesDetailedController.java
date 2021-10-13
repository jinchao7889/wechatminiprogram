package com.orange.trip.controller;

import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.trip.info.SummarizePageInfo;
import com.orange.trip.info.TripExpensesDetailedInfo;
import com.orange.trip.service.TripExpensesDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 行程花销
 */
@RestController
@RequestMapping("/trip_expenses_detailed")
public class TripExpensesDetailedController {
    @Autowired
    TripExpensesDetailedService detailedService;

    @PostMapping("/add")
    public ResponseWrapper addTripExpenses(@Validated @RequestBody TripExpensesDetailedInfo detailedInfo){
        return ResponseWrapper.markSuccess(detailedService.addTripExpenses(detailedInfo));
    }

    @GetMapping("/del/{id}")
    public ResponseWrapper delTripExpensesDetailed(@PathVariable Integer id){
        detailedService.delTripExpensesDetailed(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }

}
