package com.orange.shop.controller;

import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.shop.domain.Shop;
import com.orange.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    ShopService shopService;

    @PostMapping("/add")
    public ResponseWrapper add(@RequestBody Shop shop){
        return ResponseWrapper.markSuccess(shopService.addShop(shop));
    }

    @GetMapping("/get_all")
    public ResponseWrapper getAll(){
        return ResponseWrapper.markSuccess(shopService.getShops());
    }
    @GetMapping("/get/{id}")
    public ResponseWrapper get(@PathVariable Integer id){
        return ResponseWrapper.markSuccess(shopService.get(id));
    }

    @GetMapping("/del/{id}")
    public ResponseWrapper del(@PathVariable Integer id){
        shopService.delShop(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }
}
