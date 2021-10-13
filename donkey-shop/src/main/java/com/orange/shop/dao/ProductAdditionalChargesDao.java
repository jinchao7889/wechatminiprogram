package com.orange.shop.dao;

import com.orange.shop.domain.ProductAdditionalCharges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductAdditionalChargesDao extends JpaRepository<ProductAdditionalCharges,Long> {
    List<ProductAdditionalCharges> findAllByProductId(Long productId);

    @Query("select p from ProductAdditionalCharges p where p.optionalGrade in (:oga,:ogb) and p.productId=:id")
    List<ProductAdditionalCharges> findAllByProductIdAndOptionalGradeOrOptionalGrad(@Param("id") Long productId,@Param("oga") Integer optionalGrade,@Param("ogb") Integer optionalGrade2);
}
