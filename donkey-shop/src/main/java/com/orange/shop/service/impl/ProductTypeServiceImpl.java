package com.orange.shop.service.impl;


import com.orange.share.page.PageInfo;
import com.orange.share.vo.PageVo;
import com.orange.shop.dao.ProductTypeDao;
import com.orange.shop.domain.ProductType;
import com.orange.shop.info.ProductPageInfo;
import com.orange.shop.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeDao productTypeDao;

    @Override
    public ProductType addProductType(ProductType productType) {
        productTypeDao.saveAndFlush(productType);
        return productType;
    }

    @Override
    public void delProductType(Integer id) {
        productTypeDao.delete(id);
    }

    @Override
    public List<ProductType> getProductType(Integer shopId) {
        return productTypeDao.findAllByShopId(shopId);
    }

    @Override
    public PageVo getPage(ProductPageInfo pageInfo) {
        Pageable pageable = new PageRequest(pageInfo.getPage(),pageInfo.getSize());
        Page page =  productTypeDao.findAllByShopId(pageInfo.getShopId(),pageable);
        return new PageVo(page.getTotalPages(),pageInfo.getPage(),page.getTotalElements(),page.getContent());
    }

    @Override
    public ProductType getOne(Integer id) {
        return productTypeDao.findOne(id);
    }


}
