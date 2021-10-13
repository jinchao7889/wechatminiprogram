package com.orange.trip.controller;

import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.trip.info.TripInventoryInfo;
import com.orange.trip.service.TripInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 行程清单
 */
@RestController
@RequestMapping("/trip_inventory")
public class TripInventoryController {
    @Autowired
    TripInventoryService inventoryService;

    @PostMapping("/add")
    public ResponseWrapper addTripExpenses(@RequestBody @Validated List<TripInventoryInfo> inventoryInfo){
        return ResponseWrapper.markSuccess(inventoryService.addTripExpenses(inventoryInfo));
    }

    @GetMapping("/get/{id}")
    public ResponseWrapper getTripExpenses(@PathVariable Long id){
        return ResponseWrapper.markSuccess(inventoryService.getTripExpenses(id));
    }

    @GetMapping("/get_all/{id}")
    public ResponseWrapper getTripExpensesAll(@PathVariable Long id){
        return ResponseWrapper.markSuccess(inventoryService.getAllTripInventoryVo(id));
    }

    @GetMapping("/del/{id}")
    public ResponseWrapper del(@PathVariable Integer id){
        inventoryService.delTripInventory(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }
}
