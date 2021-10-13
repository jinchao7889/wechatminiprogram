package com.orange.trip.controller;

import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.trip.info.ElementPageInfo;
import com.orange.trip.info.TripInventoryElementInfo;
import com.orange.trip.service.TripInventoryElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 行程清单详细
 */
@RestController
@RequestMapping("/trip_inventory_element")
public class TripInventoryElementController {
    @Autowired
    TripInventoryElementService elementService;

    @PostMapping("/add")
    public ResponseWrapper addElement(@RequestBody @Validated TripInventoryElementInfo inventoryElementInfo){
        return ResponseWrapper.markSuccess(elementService.addElement(inventoryElementInfo));
    }

    @GetMapping("/get/{inventoryId}")
    public ResponseWrapper getElements(@PathVariable Integer inventoryId){
        return ResponseWrapper.markSuccess(elementService.getElements(inventoryId));
    }

    @GetMapping("/del/{id}")
    public ResponseWrapper delElement(@PathVariable Integer id){
        elementService.delElement(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }

    @GetMapping("/up/{id}/{ic}")
    public ResponseWrapper upElementCheck(@PathVariable Integer id,@PathVariable Boolean ic){
        elementService.upElementCheck(id,ic);
        return ResponseWrapper.markSuccess(ReturnCode.UPDATE_SUCCESS);
    }

    @PostMapping("/get_page")
    public ResponseWrapper getTripElement(@Validated @RequestBody ElementPageInfo pageInfo){
        return ResponseWrapper.markSuccess(elementService.getTripElement(pageInfo));
    }
}
