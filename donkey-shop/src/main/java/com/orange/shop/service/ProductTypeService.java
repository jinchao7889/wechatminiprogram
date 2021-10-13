package com.orange.shop.service;

import com.orange.share.page.PageInfo;
import com.orange.share.vo.PageVo;
import com.orange.shop.domain.ProductType;
import com.orange.shop.info.ProductPageInfo;

import java.util.List;

public interface ProductTypeService {
    ProductType addProductType(ProductType productType);
    void delProductType(Integer id);

    List<ProductType> getProductType(Integer shopId);

    PageVo getPage(ProductPageInfo pageInfo);

    ProductType getOne(Integer id);
}
