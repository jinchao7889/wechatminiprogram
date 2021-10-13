package com.orange.shop.controller;

import com.orange.share.response.ResponseWrapper;
import com.orange.shop.domain.ProductAdditionalCharges;
import com.orange.shop.service.ProductAdditionalChargesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/product_additional_charges")
public class ProductAdditionalChargesController {
    @Autowired
    ProductAdditionalChargesService additionalChargesService;

    @PostMapping("/add")
    public ResponseWrapper add(@RequestBody ProductAdditionalCharges additionalCharges){
        return ResponseWrapper.markSuccess(additionalChargesService.add(additionalCharges));
    }

    @GetMapping("/get/{productId}")
    public ResponseWrapper add(@PathVariable Long productId){
        return ResponseWrapper.markSuccess(additionalChargesService.get(productId));
    }
}
