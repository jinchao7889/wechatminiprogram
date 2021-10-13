package com.orange.shop.service.impl;

import com.orange.shop.dao.ShopDao;
import com.orange.shop.domain.Shop;
import com.orange.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    ShopDao shopDao;
    @Override
    public Shop addShop(Shop shop) {
        shopDao.saveAndFlush(shop);
        return shop;
    }

    @Override
    public List<Shop> getShops() {
        return shopDao.findAll();
    }

    @Override
    public void delShop(Integer id) {
        shopDao.delete(id);
    }

    @Override
    public Shop get(Integer id) {
        return shopDao.findOne(id);
    }
}
