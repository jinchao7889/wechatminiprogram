package com.orange.order.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 支付记录表
 */
@Data
@Table(name = "pay_money")
@Entity
public class OrderPayHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(name ="pay_money" )
    BigDecimal payMoney;

    @Column(name = "order_id",length = 50)
    String orderId;

    @Column(name = "order_money")
    BigDecimal orderMoney;

    /**
     * 支付方式
     * 1.余额支付
     * 2.微信支付
     */
    @Column(name="payment_type")
    Integer paymentType;

    /**
     * 支付的返回信息
     */
    @Column(name = "pay_json")
    String payJson;
    String remark;
}
