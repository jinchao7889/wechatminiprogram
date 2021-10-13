package com.orange.shop.controller;

import com.orange.share.constant.ReturnCode;
import com.orange.share.page.PageInfo;
import com.orange.share.response.ResponseWrapper;
import com.orange.shop.domain.ProductType;
import com.orange.shop.info.ProductPageInfo;
import com.orange.shop.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product_type")
public class ProductTypeController {
    @Autowired
    ProductTypeService productTypeService;

    @PostMapping("/add")
    public ResponseWrapper add(@RequestBody ProductType productType){
        return ResponseWrapper.markSuccess(productTypeService.addProductType(productType));
    }

    @GetMapping("/get/{shopId}")
    public ResponseWrapper get(@PathVariable Integer shopId){
        return ResponseWrapper.markSuccess(productTypeService.getProductType(shopId));
    }

    @GetMapping("/del/{id}")
    public ResponseWrapper del(@PathVariable Integer id){
        productTypeService.delProductType(id);
        return ResponseWrapper.markSuccess(ReturnCode.DELETE_SUCCESS.getMsg());
    }
    @GetMapping("/get_one/{id}")
    public ResponseWrapper getOne(@PathVariable Integer id){

        return ResponseWrapper.markSuccess(productTypeService.getOne(id));
    }
    @PostMapping("/get_page")
    public ResponseWrapper getPage(@RequestBody ProductPageInfo pageInfo){
        return ResponseWrapper.markSuccess(productTypeService.getPage(pageInfo));
    }

}
