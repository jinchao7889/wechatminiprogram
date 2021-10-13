package com.orange.activity.controller;

import com.orange.activity.info.ActivityLeaveMessageInfo;
import com.orange.activity.info.ActivityLeaveMessagePageInfo;
import com.orange.activity.service.ActivityLeaveMessageService;
import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity_leave_message")
public class ActivityLeaveMessageController {
    @Autowired
    ActivityLeaveMessageService leaveMessageService;

    @PostMapping("/add")
    public ResponseWrapper add(@RequestBody @Validated ActivityLeaveMessageInfo info){
        return ResponseWrapper.markSuccess(leaveMessageService.addComment(info));
    }
    @PostMapping("/get_page")
    public ResponseWrapper getPage(@RequestBody @Validated ActivityLeaveMessagePageInfo info){
        return ResponseWrapper.markSuccess(leaveMessageService.getPageComment(info));
    }
    @PostMapping("/sec/get_page")
    public ResponseWrapper getSecPage(@RequestBody @Validated ActivityLeaveMessagePageInfo info){
        return ResponseWrapper.markSuccess(leaveMessageService.getPageSecComment(info));
    }
    @GetMapping("/del/{id}")
    public ResponseWrapper delData(@PathVariable Integer id){
        leaveMessageService.delComment(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }
}

