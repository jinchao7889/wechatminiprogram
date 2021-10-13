package com.orange.person.controller;

import com.orange.person.service.FollowService;
import com.orange.share.constant.ReturnCode;
import com.orange.share.page.PageInfo;
import com.orange.share.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    FollowService followService;

    @GetMapping("/add/{beUserId}")
    public ResponseWrapper addFollow(@PathVariable String beUserId){
        return ResponseWrapper.markSuccess(followService.addFollow(beUserId));
    }

    @GetMapping("/cancel/{beUserId}")
    public ResponseWrapper cancelFollow(@PathVariable String beUserId){
        followService.cancelFollow(beUserId);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }
    @PostMapping("/get_owner_page")
    public ResponseWrapper getPage(@RequestBody PageInfo pageInfo){

        return ResponseWrapper.markSuccess(followService.getOwnerPage(pageInfo));
    }
}
