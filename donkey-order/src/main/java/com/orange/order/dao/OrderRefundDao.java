package com.orange.order.dao;

import com.orange.order.domain.OrderRefund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRefundDao extends JpaRepository<OrderRefund,String> {
    @Modifying
    @Query("update OrderRefund as O set O.status=:s where O.id=:id")
    int updateStatus(@Param("id") String id, @Param("s") int state);

    OrderRefund queryByOrderId(String orderId);
}