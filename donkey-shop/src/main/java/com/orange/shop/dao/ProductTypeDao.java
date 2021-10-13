package com.orange.shop.dao;

import com.orange.shop.domain.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTypeDao extends JpaRepository<ProductType,Integer> {

    List<ProductType> findAllByShopId(Integer shopId);

    Page<ProductType> findAllByShopId(Integer shopId, Pageable pageable);
}
