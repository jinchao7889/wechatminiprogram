package com.orange.shop.service.impl;

import com.orange.shop.dao.ProductAdditionalChargesDao;
import com.orange.shop.domain.ProductAdditionalCharges;
import com.orange.shop.service.ProductAdditionalChargesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAdditionalChargesServiceImpl implements ProductAdditionalChargesService {
    @Autowired
    ProductAdditionalChargesDao additionalChargesDao;
    @Override
    public ProductAdditionalCharges add(ProductAdditionalCharges additionalCharges) {
        additionalChargesDao.save(additionalCharges);
        return additionalCharges;
    }

    @Override
    public List<ProductAdditionalCharges> get(Long productId) {
        return additionalChargesDao.findAllByProductId(productId);
    }
}
