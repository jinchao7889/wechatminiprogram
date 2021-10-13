package com.orange.shop.service;

import com.orange.shop.domain.ProductAdditionalCharges;

import java.util.List;

public interface ProductAdditionalChargesService {
     ProductAdditionalCharges add(ProductAdditionalCharges additionalCharges);
    List<ProductAdditionalCharges> get(Long productId);
}
