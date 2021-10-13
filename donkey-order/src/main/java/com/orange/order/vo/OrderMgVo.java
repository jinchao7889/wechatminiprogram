package com.orange.order.vo;

import com.orange.order.domain.OrderProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderMgVo {
    String orderId;
    String shopName;
    String shopImg;
    Integer shopId;
    String shopUrl;
    Integer orderState;

    List<OrderProduct> orderProducts;

    /**
     * 订单金额
     */
    BigDecimal totalReward;
    /**
     * 实际付款
     */
    BigDecimal payMoney;

    /**
     * 订单内型
     */
    Integer orderType;

    /**
     * 付款时间
     */
    Long payTime;

    Long createTime;

    String nickname;
    String userId;

    Integer orderMold;



    Long consignTime;

    Long completionTime;


    Long cancelTime;
}
