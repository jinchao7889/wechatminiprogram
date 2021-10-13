package com.orange.shop.controller;

import com.orange.share.constant.ReturnCode;
import com.orange.share.page.PageInfo;
import com.orange.share.response.ResponseWrapper;
import com.orange.shop.domain.ProductComment;
import com.orange.shop.info.ProductCommentPageInfo;
import com.orange.shop.service.ProductCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product_comment")
public class ProductCommentController {
    @Autowired
    ProductCommentService commentService;

    @PostMapping("/add")
    public ResponseWrapper addProductComment(@RequestBody ProductComment productComment){
        return ResponseWrapper.markSuccess(commentService.addProductComment(productComment));
    }

    @PostMapping("/get_page")
    public ResponseWrapper getPage(@RequestBody ProductCommentPageInfo pageInfo){
        return ResponseWrapper.markSuccess(commentService.getPageProductComment(pageInfo));
    }

    @GetMapping("/del")
    public ResponseWrapper del(@PathVariable String id){
        commentService.delProductComment(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }
}
