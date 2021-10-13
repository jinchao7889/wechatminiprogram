package com.orange.person.controller;

import com.orange.person.domain.UserAuthentication;
import com.orange.person.dto.AuthenticationDto;
import com.orange.person.info.AuditPageInfo;
import com.orange.person.service.UserAuthenticationService;
import com.orange.share.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_authentication")
public class UserAuthenticationController {
    @Autowired
    UserAuthenticationService userAuthenticationService;

    @PostMapping("/add")
    public ResponseWrapper add(@RequestBody UserAuthentication userAuthentication){
        return ResponseWrapper.markSuccess(userAuthenticationService.addUserAuthentication(userAuthentication));
    }

    @GetMapping("/get_owner")
    public ResponseWrapper getOwner(){
        return ResponseWrapper.markSuccess(userAuthenticationService.getAuthentication());
    }

    @PostMapping("/up")
    public ResponseWrapper up(@Validated @RequestBody AuthenticationDto authenticationDto){
        return ResponseWrapper.markSuccess(userAuthenticationService.upAuthentication(authenticationDto));
    }
    @PostMapping("/get_page")
    public ResponseWrapper getPage(@RequestBody AuditPageInfo info) {
        return ResponseWrapper.markSuccess(userAuthenticationService.getPage(info));
    }

    @GetMapping("/get/{userId}")
    public ResponseWrapper get(@PathVariable String userId ){
        return ResponseWrapper.markSuccess(userAuthenticationService.getByUserId(userId));
    }
}
