package com.orange.shop.service;

import com.orange.shop.domain.ProductSpecs;

public interface ProductSpecsService {
    /**
     * 新增商品规格
     * @param productSpecs
     * @return
     */
    ProductSpecs addProductSpecs(ProductSpecs productSpecs);

    void delProductSpecs(Long id);
}
