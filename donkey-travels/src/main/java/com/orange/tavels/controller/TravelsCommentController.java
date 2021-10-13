package com.orange.tavels.controller;

import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.tavels.info.CommentInfo;
import com.orange.tavels.info.CommentPageInfo;
import com.orange.tavels.service.TravelsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 游记评价
 */
@RestController
@RequestMapping("/travels_comment")
public class TravelsCommentController {
    @Autowired
    TravelsCommentService commentService;

    @PostMapping("/add")
    public ResponseWrapper add(@RequestBody @Validated CommentInfo info){
        return ResponseWrapper.markSuccess(commentService.addComment(info));
    }
    @PostMapping("/get_page")
    public ResponseWrapper getPage(@RequestBody @Validated CommentPageInfo info){
        return ResponseWrapper.markSuccess(commentService.getPageComment(info));
    }
    @PostMapping("/sec/get_page")
    public ResponseWrapper getSecPage(@RequestBody @Validated CommentPageInfo info){
        return ResponseWrapper.markSuccess(commentService.getPageSecComment(info));
    }
    @GetMapping("/del/{id}")
    public ResponseWrapper delData(@PathVariable Integer id){
        commentService.delComment(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }
}
