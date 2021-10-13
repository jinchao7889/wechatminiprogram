package com.orange.shop.controller;


import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.shop.domain.ShopCarouselImg;
import com.orange.shop.service.ShopCarouselImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop_carousel_img")
public class ShopCarouselImgController {
    @Autowired
    ShopCarouselImgService shopCarouselImgService;

    @PostMapping("/add")
    public ResponseWrapper add(@RequestBody ShopCarouselImg shopCarouselImg){
        return ResponseWrapper.markSuccess(shopCarouselImgService.add(shopCarouselImg));
    }
    @PostMapping("/adds")
    public ResponseWrapper add(@RequestBody List<ShopCarouselImg> shopCarouselImgs){
        return ResponseWrapper.markSuccess(shopCarouselImgService.adds(shopCarouselImgs));
    }

    @GetMapping("/del/{id}")
    public ResponseWrapper del(@PathVariable Long id){
        shopCarouselImgService.del(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }

    @GetMapping("/get/{shopId}")
    public ResponseWrapper get(@PathVariable Integer shopId){
        return ResponseWrapper.markSuccess(shopCarouselImgService.getUp(shopId));
    }

    @GetMapping("/get/mg/{shopId}")
    public ResponseWrapper getMg(@PathVariable Integer shopId){
        return ResponseWrapper.markSuccess(shopCarouselImgService.get(shopId));
    }
}
