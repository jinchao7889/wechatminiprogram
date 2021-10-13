package com.orange.shop.controller;

import com.orange.share.response.ResponseWrapper;
import com.orange.shop.domain.ProductIntroduceContent;
import com.orange.shop.service.ProductIntroduceContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product_introduce_content")
public class ProductIntroduceContentController {
    @Autowired
    ProductIntroduceContentService contentService;

    @PostMapping("/add")
    public ResponseWrapper add(@RequestBody ProductIntroduceContent introduceContent){
        return ResponseWrapper.markSuccess(contentService.addProductDetailContent(introduceContent));
    }
}
