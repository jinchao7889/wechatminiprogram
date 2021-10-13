package com.orange.shop.dao;

import com.orange.shop.domain.ProductSpecs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductSpecsDao extends JpaRepository<ProductSpecs,Long> {
    @Query("update ProductSpecs p set p.enable=false where p.id=:id")
    int updateEnable(@Param("id") Long id);


    List<ProductSpecs> findAllByProductIdAndEnable(Long productId,Boolean enable);
}
