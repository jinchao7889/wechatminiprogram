package com.orange.order.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Table(name = "tb_order")
@Entity
public class Order {
    @Id
    @Column(name ="id",length = 50)
    String id;
    /**
     * 总的金额
     */
    @Column(name="total_reward")
    BigDecimal totalReward;
    @Column(name="user_id",length = 32)
    String userId;
    /**
     * 备注
     */
    @Column(name="remarks",length = 50)
    String remarks;
    /**
     * 支付方式
     * 1.余额支付
     * 2.微信支付
     */
    @Column(name="payment_type")
    Integer paymentType;

    /**
     * 下单时间
     */
    @Column(name="create_time")
    Long createTime;
    /**
     * 付款时间
     */
    @Column(name="pay_time")
    Long payTime;
    @Column(name = "contacts",length = 20)
    private String contacts;
    @Column(name = "phone",length = 20)
    private String phone;
    /**
     * 大致地址
     */
    @Column(name = "approximately_address",length = 50)
    private String approximatelyAddress;
    /**
     * 详细地址
     */
    @Column(name = "detailed_address",length = 50)
    private String detailedAddress;

    @Column(name ="state" )
    Integer orderState;

    @Column(name ="enable" )
    Boolean enable=true;

    @Column(name ="evaluate_series" )
    Integer evaluateSeries;

    @Column(name ="evaluate_content" )
    String evaluateContent;

    /**
     * 是否使用优惠券
     */
    @Column(name ="use_coupon" )
    Boolean useCoupon;
    /**
     * 优惠券ID
     */
    @Column(name ="coupon_id" )
    String couponId;
    /**
     * 支付金额
     */
    @Column(name ="pay_money" )
    BigDecimal payMoney;


    /**
     * 配送方式
     */
    @Column(name = "deliver_type")
    Integer  deliverType;

    /**
     * 运费
     */
    @Column(name = "deliver_money")
    BigDecimal deliverMoney;

    /**
     * 商品项数量
     */
    @Column(name = "item_count")
    Integer itemCount;
    /**
     * 商户ID
     */
    @Column(name = "shop_id")
    Integer shopId;
    /**
     * 订单商品类型
     * 1 普通订单
     * 2 租赁订单
     */
    @Column(name = "order_type")
    Integer orderType;

    /**
     * 租赁开始时间
     */
    Long startTime;
    /**
     * 租赁结束时间
     */
    Long endTime;
    /**
     * 订单过期时间
     */
    Long timeExpire;

    /**
     * 旅游型商品订单
     */
    @Column(name = "order_mold")
    Integer orderMold;

    /**
     * 发货时间
     */
    @Column(name = "consign_time")
    Long consignTime;

    @Column(name = "received_time")
    Long receivedTime;
    /**
     * 完成时间
     */
    @Column(name = "completion_time")
    Long completionTime;

    /**
     * 订单取消时间
     */
    @Column(name = "cancel_time")
    Long cancelTime;
}
