package com.orange.shop.dao;

import com.orange.shop.domain.ShopCarouselImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopCarouselImgDao extends JpaRepository<ShopCarouselImg,Long> {
    List<ShopCarouselImg> findAllByShopIdOrderBySerialNumberAsc(Integer shopId);
    List<ShopCarouselImg> findAllByShopIdAndUpperShelfOrderBySerialNumberAsc(Integer shopId,Boolean upperShelf);
}
