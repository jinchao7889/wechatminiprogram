package com.orange.order.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 订单附加收费
 */
@Data
@Table(name = "order_additional_charges")
@Entity
public class OrderAdditionalCharges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "order_id")
    String orderId;
    /**
     * 附加收费名称
     */
    @Column(name = "product_name")
    String additionalChargesName;
    /**
     * 收费价格
     */
    @Column(name = "charges_price")
    BigDecimal chargesPrice;
}
