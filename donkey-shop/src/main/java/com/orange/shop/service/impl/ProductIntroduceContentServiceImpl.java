package com.orange.shop.service.impl;

import com.orange.shop.dao.ProductIntroduceContentDao;
import com.orange.shop.domain.ProductIntroduceContent;
import com.orange.shop.service.ProductIntroduceContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductIntroduceContentServiceImpl implements ProductIntroduceContentService {
    @Autowired
    ProductIntroduceContentDao contentDao ;
    @Override
    public ProductIntroduceContent addProductDetailContent(ProductIntroduceContent detailContent) {
        contentDao.save(detailContent);
        return detailContent;
    }
}
