package com.orange.activity.controller;

import com.orange.activity.domain.ActivityEnter;
import com.orange.activity.dto.ActivityEnterDto;
import com.orange.activity.info.AcivityEnterPageInfo;
import com.orange.activity.service.ActivityEnterService;
import com.orange.share.constant.ReturnCode;
import com.orange.share.page.PageInfo;
import com.orange.share.response.ResponseWrapper;
import com.orange.share.util.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/activity_enter")
public class ActivityEnterController {
    @Autowired
    ActivityEnterService activityEnterService;

    @RequestMapping("/add")
    public ResponseWrapper add(@RequestBody ActivityEnterDto activityEnterDto, HttpServletRequest request){
        return ResponseWrapper.markSuccess( activityEnterService.addActivityEnter(activityEnterDto, IPUtil.getIpAddr(request)));
    }

    @RequestMapping("/get_page")
    public ResponseWrapper getPage(@RequestBody PageInfo pageInfo){
        return ResponseWrapper.markSuccess(activityEnterService.getPage(pageInfo));
    }
    @GetMapping("/get/{id}")
    public  ResponseWrapper getActivityEnter(@PathVariable Long id){
        return ResponseWrapper.markSuccess(activityEnterService.getActivityEnter(id));
    }

    @PostMapping("/mg/get_page")
    public  ResponseWrapper getMgPage(@RequestBody AcivityEnterPageInfo pageInfo){
        return ResponseWrapper.markSuccess(activityEnterService.getMgPage(pageInfo));
    }

    @GetMapping("/get_enter/{activityId}")
    public ResponseWrapper getEnter(@PathVariable String activityId){
        return ResponseWrapper.markSuccess(activityEnterService.getAll(activityId));
    }

    @GetMapping("/cancel/{id}")
    public ResponseWrapper cancelEnter(@PathVariable Long id){
        activityEnterService.cancelEnter(id);
        return ResponseWrapper.markSuccess(ReturnCode.UPDATE_SUCCESS);
    }
}
