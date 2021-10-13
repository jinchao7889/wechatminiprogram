package com.orange.person.controller;

import com.orange.person.info.UserHeadImgInfo;
import com.orange.person.service.UserBaseService;
import com.orange.share.constant.ReturnCode;
import com.orange.share.page.PageInfo;
import com.orange.share.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserBaseController {
    @Autowired
    UserBaseService userBaseService;

    @RequestMapping("/get_page")
    public ResponseWrapper getPage(@RequestBody PageInfo pageInfo){
        return ResponseWrapper.markSuccess(userBaseService.getPage(pageInfo));
    }

    @PostMapping("/update/portrait")
    public ResponseWrapper updatePortrait(@RequestBody UserHeadImgInfo imgInfo){

        return ResponseWrapper.markSuccess(userBaseService.updateP(imgInfo.getUrl()));
    }
}
