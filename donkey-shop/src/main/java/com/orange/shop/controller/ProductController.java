package com.orange.shop.controller;

import com.orange.share.constant.ReturnCode;
import com.orange.share.response.ResponseWrapper;
import com.orange.shop.domain.Product;
import com.orange.shop.dto.ProductDto;
import com.orange.shop.info.ProductMgPageInfo;
import com.orange.shop.info.ProductPageInfo;
import com.orange.shop.info.ProductSellWellPageInfo;
import com.orange.shop.info.ProductUpPageInfo;
import com.orange.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseWrapper addProduct(@RequestBody ProductDto product){
        return ResponseWrapper.markSuccess(productService.addProduct(product));
    }

    @PostMapping("/get_page")
    public ResponseWrapper getPage(@RequestBody ProductPageInfo pageInfo){
        return ResponseWrapper.markSuccess(productService.getPageProduct(pageInfo));
    }

    @PostMapping("/get_all_page")
    public ResponseWrapper getAllPage(@RequestBody ProductUpPageInfo pageInfo){
        return ResponseWrapper.markSuccess(productService.getAllPageProduct(pageInfo));
    }
    @GetMapping("/get/{id}")
    public ResponseWrapper getProductDetail(@PathVariable Long  id){
        return ResponseWrapper.markSuccess(productService.getProductDetail(id));
    }

    @PostMapping("/get_sell_well")
    public ResponseWrapper getProductSellWell(@RequestBody ProductSellWellPageInfo pageInfo){
        return ResponseWrapper.markSuccess(productService.getSellWellProduct(pageInfo));
    }

    @GetMapping("/isupShelf/{id}/{shelf}")
    public ResponseWrapper upUpperShelf(@PathVariable Integer id,@PathVariable Boolean shelf){
        productService.upUpperShelf(id.longValue(),shelf);
        return ResponseWrapper.markSuccess(ReturnCode.UPDATE_SUCCESS);
    }
    @GetMapping("/isup/{id}/{stock}")
    public ResponseWrapper upStock(@PathVariable Long id,@PathVariable Integer stock){
        productService.upProductStock(id,stock);
        return ResponseWrapper.markSuccess(ReturnCode.UPDATE_SUCCESS);
    }

    @PostMapping("/mg/get_page")
    public ResponseWrapper getPage(@RequestBody ProductMgPageInfo pageInfo){
        return ResponseWrapper.markSuccess(productService.getPageMgProduct(pageInfo));
    }
}
