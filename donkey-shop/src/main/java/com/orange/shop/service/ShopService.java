package com.orange.shop.service;

import com.orange.shop.domain.Shop;

import java.util.List;

public interface ShopService {
    Shop addShop(Shop shop);
    List<Shop> getShops();

    void delShop(Integer id);

    Shop get(Integer id);
}
