package com.orange.shop.service.impl;

import com.orange.shop.dao.ProductSpecsDao;
import com.orange.shop.domain.ProductSpecs;
import com.orange.shop.service.ProductSpecsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSpecsServiceImpl implements ProductSpecsService {
    @Autowired
    ProductSpecsDao productSpecsDao;

    @Override
    public ProductSpecs addProductSpecs(ProductSpecs productSpecs) {
        productSpecsDao.saveAndFlush(productSpecs);
        return productSpecs;
    }

    @Override
    public void delProductSpecs(Long id) {
        productSpecsDao.updateEnable(id);
    }
}
