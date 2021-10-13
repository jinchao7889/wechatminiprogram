package com.orange.tavels.controller;

import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.tavels.dto.TravelContentDto;
import com.orange.tavels.info.TravelsInfo;
import com.orange.tavels.info.TravelsPageInfo;
import com.orange.tavels.service.TravelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travels")
public class TravelsController {
    @Autowired
    TravelsService travelsService;

    @PostMapping("/add")
    public ResponseWrapper addTravels(@RequestBody @Validated TravelsInfo info){
        return ResponseWrapper.markSuccess(travelsService.addTravels(info));
    }

    @PostMapping("/get_page")
    public ResponseWrapper getTravels(@RequestBody @Validated TravelsPageInfo pageInfo){
        return ResponseWrapper.markSuccess(travelsService.getPage(pageInfo));
    }
    @PostMapping("/mg/get_page")
    public ResponseWrapper getTMgravels(@RequestBody @Validated TravelsPageInfo pageInfo){
        return ResponseWrapper.markSuccess(travelsService.getMgPage(pageInfo));
    }
    @PostMapping("/owner/get_page")
    public ResponseWrapper getOwnerTravels(@RequestBody @Validated TravelsPageInfo pageInfo){
        return ResponseWrapper.markSuccess(travelsService.getOwnerPage(pageInfo));
    }

    @GetMapping("/get_content/{travelsId}")
    public ResponseWrapper getContent(@PathVariable String travelsId){
        return ResponseWrapper.markSuccess(travelsService.getTravelsContent(travelsId));
    }

    @GetMapping("/get_travels/{travelsId}")
    public ResponseWrapper getTravels(@PathVariable String travelsId){
        return ResponseWrapper.markSuccess(travelsService.getTravels(travelsId));
    }

    @PostMapping("/update_content")
    public ResponseWrapper getTravelsContent(@RequestBody TravelContentDto contentDto){
        return ResponseWrapper.markSuccess(travelsService.upContent(contentDto));
    }

    @GetMapping("/del/{travelsId}")
    public ResponseWrapper delTravels(@PathVariable String  travelsId){
        travelsService.delTravels(travelsId);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }
    @GetMapping("/release/{travelsId}")
    public ResponseWrapper releaseTravels(@PathVariable String  travelsId){
        travelsService.release(travelsId);
        return ResponseWrapper.markSuccess(ReturnCode.UPDATE_SUCCESS);
    }
    @GetMapping("/updateStatus/{travelsId}/{status}")
    public ResponseWrapper releaseTravels(@PathVariable String  travelsId,@PathVariable Integer status){
        travelsService.updateStatusTravels(travelsId,status);
        return ResponseWrapper.markSuccess(ReturnCode.UPDATE_SUCCESS);
    }
}
