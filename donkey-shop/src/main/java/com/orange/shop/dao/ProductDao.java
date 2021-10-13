package com.orange.shop.dao;

import com.orange.shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductDao extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {

    @Modifying
    @Query("update Product p set p.isUpperShelf=:ius where p.id=:id")
    int updateUpperShelf(@Param("id") Long id,@Param("ius") Boolean isUpperShelf);

    @Modifying
    @Query("update Product p set p.productStock=:ps where p.id=:id")
    int updateProductStock(@Param("id") Long id,@Param("ps") Integer stock);
}
