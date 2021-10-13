package com.orange.shop.dao;

import com.orange.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopDao extends JpaRepository<Shop,Integer> {
}
