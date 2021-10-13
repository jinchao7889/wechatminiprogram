package com.orange.shop.service.impl;

import com.orange.shop.dao.ShopCarouselImgDao;
import com.orange.shop.domain.ShopCarouselImg;
import com.orange.shop.service.ShopCarouselImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCarouselImgServiceImpl implements ShopCarouselImgService {
    @Autowired
    ShopCarouselImgDao shopCarouselImgDao;

    @Override
    public ShopCarouselImg add(ShopCarouselImg carouselImg) {
        shopCarouselImgDao.saveAndFlush(carouselImg);
        return carouselImg;
    }

    @Override
    public List<ShopCarouselImg> adds(List<ShopCarouselImg> carouselImgs) {
        for (ShopCarouselImg carouselImg :carouselImgs){
            shopCarouselImgDao.saveAndFlush(carouselImg);
        }
        return carouselImgs;
    }

    @Override
    public void del(Long id) {
        shopCarouselImgDao.delete(id);
    }

    @Override
    public List<ShopCarouselImg> getUp(Integer shopId) {
        return shopCarouselImgDao.findAllByShopIdAndUpperShelfOrderBySerialNumberAsc(shopId,true);
    }

    @Override
    public List<ShopCarouselImg> get(Integer shopId) {
        return shopCarouselImgDao.findAllByShopIdOrderBySerialNumberAsc(shopId);
    }
}
