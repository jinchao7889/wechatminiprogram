package com.orange.shop.controller;

import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.shop.domain.ProductSpecs;
import com.orange.shop.service.ProductSpecsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product_specs")
public class ProductSpecsController {
    @Autowired
    ProductSpecsService productSpecsService;

    @PostMapping("/add")
    public ResponseWrapper add(@RequestBody ProductSpecs productSpecs){
        return ResponseWrapper.markSuccess(productSpecsService.addProductSpecs(productSpecs)) ;
    }
    @GetMapping("/del/{id}")
    public ResponseWrapper del(@PathVariable Long id){
        productSpecsService.delProductSpecs(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS);
    }
}
