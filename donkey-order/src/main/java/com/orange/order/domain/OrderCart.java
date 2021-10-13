package com.orange.order.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 购物车表
 */
@Data
@Entity
@Table(name = "order_cart")
public class OrderCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "shop_id")
    String shopId;

    @Column(name = "user_id")
    String userId;

    @Column(name = "product_name")
    String productName;

    @Column(name = "product_count")
    Integer productCount;

    @Column(name = "product_price")
    BigDecimal productPrice;

    /**
     * 购物车状态
     */
    Integer cartStatus;
}
