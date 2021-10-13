package com.orange.order.dao;

import com.orange.order.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductDao extends JpaRepository<OrderProduct,Long> {
    List<OrderProduct> findAllByOrderId(String orderId);
}
