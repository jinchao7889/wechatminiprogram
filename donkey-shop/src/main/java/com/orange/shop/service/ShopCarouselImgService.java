package com.orange.shop.service;

import com.orange.shop.domain.ShopCarouselImg;

import java.util.List;

public interface ShopCarouselImgService {

    ShopCarouselImg add(ShopCarouselImg carouselImg);

    List<ShopCarouselImg> adds(List<ShopCarouselImg> carouselImg);

    void del(Long id);
    List<ShopCarouselImg> getUp(Integer shopId);
    List<ShopCarouselImg> get(Integer shopId);
}
