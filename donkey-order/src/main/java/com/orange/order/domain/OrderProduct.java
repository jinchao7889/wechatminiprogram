package com.orange.order.domain;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 订单商品表
 */
@Data
@Table(name = "order_product")
@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "order_id")
    String orderId;

    @Column(name = "shop_id")
    Integer shopId;

    @Column(name = "product_name")
    String productName;

    @Column(name = "product_decs")
    String productDecs;

    @Column(name = "product_count")
    Integer productCount;

    @Column(name = "product_price")
    BigDecimal productPrice;

    @Column(name = "product_thumbnail")
    String productThumbnail;

    @Column(name = "product_id")
    Long productId;
    @Column(name = "is_specs")
    Boolean isSpecs;

    @Column(name = "specs_id")
    Long specsId;

    @Column(name = "specs_dec")
    String specsDec;

    @Column(name = "product_deposit")
    BigDecimal productDeposit;

    @Column(name = "product_sale_type")
    Integer productSaleType;

    /**
     * 是否支付押金
     */
    @Column(name = "pay_deposit")
    Boolean authenticationDeposit;


}
