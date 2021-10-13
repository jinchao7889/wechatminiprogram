package com.orange.order.vo;

import com.orange.order.domain.OrderAdditionalCharges;
import com.orange.order.domain.OrderProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDetailVo {
    /**
     * 订单编号
     */
    String orderId;

    BigDecimal totalReward;

    String userId;
    /**
     * 备注
     */
    String remarks;
    /**
     * 支付方式
     * 1.余额支付
     * 2.微信支付
     */
    Integer paymentType;

    Long createTime;

    private String contacts;

    private String phone;
    /**
     * 大致地址
     */
    private String approximatelyAddress;
    /**
     * 详细地址
     */
    private String detailedAddress;

    Integer orderState;

    Boolean enable=true;

    Integer evaluateSeries;

    String evaluateContent;

    /**
     * 是否使用优惠券
     */
    Boolean useCoupon;
    /**
     * 优惠券ID
     */
    String couponId;
    /**
     * 支付金额
     */
    BigDecimal payMoney;


    /**
     * 配送方式
     */
    Integer  deliverType;

    /**
     * 运费
     */
    BigDecimal deliverMoney;

    /**
     * 商品项数量
     */
    Integer itemCount;

    Long startTime;
    /**
     * 租赁结束时间
     */
    Long endTime;
    Long durationTime;
    /**
     * 订单商品
     */
    List<OrderProduct> orderProducts;

    /**
     * 订单附加收费项
     */
    List<OrderAdditionalCharges> charges;

    public Long getDurationTime() {
        if (endTime!=null&&startTime!=null){
            return (endTime-startTime)/(3600*24)+1;

        }else {
            return null;
        }
    }
    Integer orderType;
    Long timeExpire;
    Integer orderMold;



    Long consignTime;

    Long completionTime;


    Long cancelTime;

    Long payTime;

    Long receivedTime;
}
