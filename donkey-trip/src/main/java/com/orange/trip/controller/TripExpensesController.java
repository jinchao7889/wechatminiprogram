package com.orange.trip.controller;

import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.trip.dto.TripExpensesDto;
import com.orange.trip.service.TripExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trip_expenses")
public class TripExpensesController  {
    @Autowired
    TripExpensesService tripExpensesService ;

    @PostMapping("/add")
    public ResponseWrapper add(@Validated @RequestBody List<TripExpensesDto> list){
        return ResponseWrapper.markSuccess(tripExpensesService.add(list));
    }
    @GetMapping("/get_all/{tripId}")
    public ResponseWrapper getAll(@PathVariable Long tripId){
        return ResponseWrapper.markSuccess(tripExpensesService.getAll(tripId));
    }

    @GetMapping("/del/{id}")
    public ResponseWrapper del(@PathVariable Integer id){
        tripExpensesService.delExpenses(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }
}
