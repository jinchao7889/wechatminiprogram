package com.orange.order.dao;

import com.orange.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDao extends JpaRepository<Order,String> , JpaSpecificationExecutor<Order> {

    @Modifying
    @Query("update Order as o set o.orderState=:st where o.id=:id")
    int updatOrderState(@Param("id") String id,@Param("st") Integer status);
}
