package com.orange.activity.controller;

import com.orange.activity.dto.ActivityDto;
import com.orange.activity.info.ActivityPageInfo;
import com.orange.activity.service.ActivityService;
import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    ActivityService activityService;

    @RequestMapping("/add")
    public ResponseWrapper add(@RequestBody ActivityDto activityDto){
        return ResponseWrapper.markSuccess(activityService.addActivity(activityDto));
    }

    @RequestMapping("/get_page")
    public ResponseWrapper getPage(@RequestBody ActivityPageInfo activityPageInfo){
        return ResponseWrapper.markSuccess(activityService.getPageActivity(activityPageInfo));
    }

    @RequestMapping("/mg/get_page")
    public ResponseWrapper getMgPage(@RequestBody ActivityPageInfo activityPageInfo){
        return ResponseWrapper.markSuccess(activityService.getMgPageActivity(activityPageInfo));
    }

    @GetMapping("/get/{id}")
    public  ResponseWrapper getActivity(@PathVariable String id){
        return ResponseWrapper.markSuccess(activityService.getActivity(id));
    }
    @GetMapping("/finish/{id}/{status}")
    public  ResponseWrapper finishActivity(@PathVariable String id , @PathVariable Integer status){
        activityService.updateActivity(id,status);
        return ResponseWrapper.markSuccess(ReturnCode.UPDATE_SUCCESS);
    }
    @GetMapping("del/carouseImg/{id}")
    public  ResponseWrapper delImg(@PathVariable Integer id){
        activityService.delImg(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }

    @GetMapping("get_like/{title}")
    public ResponseWrapper getLike(@PathVariable String title){
        return ResponseWrapper.markSuccess(activityService.getActivityQuery(title));
    }


}
