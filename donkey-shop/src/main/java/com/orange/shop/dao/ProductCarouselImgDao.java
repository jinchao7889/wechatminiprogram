package com.orange.shop.dao;

import com.orange.shop.domain.ProductCarouselImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCarouselImgDao extends JpaRepository<ProductCarouselImg,Long> {
    List<ProductCarouselImg> findAllByProductId(Long productId);
}
