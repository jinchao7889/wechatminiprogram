package com.orange.order.dao;

import com.orange.order.domain.OrderAdditionalCharges;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderAdditionalChargesDao extends JpaRepository<OrderAdditionalCharges,Long> {
    List<OrderAdditionalCharges> findAllByOrderId(String orderId);
}
