package com.orange.order.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;


@Table(name = "order_refund")
@Entity
@Data
public class OrderRefund {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    String id;

    /**
     * 退款订单号
     */
    @Column(name = "order_id",length = 32)

    String orderId;
    @Column(name = "user_id",length = 32)
    String userId;

    /**
     * 退款金额
     */
    @Column(name = "refund_fee")
    BigDecimal refundFee;

    /**
     * 退款状态
     * 1.退款处理中
     * 2.退款成功
     * 3.退款失败
     */
    Integer status;

    /**
     * 处理留言
     */
    @Column(name = "examine_msg")

    String examineMsg;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    Long updateTime;

    /**
     * 退款原因
     */
    @Column(name = "refund_reason")
    String refundReason;
    @Column(name = "create_time")
    Long createTime;
}
