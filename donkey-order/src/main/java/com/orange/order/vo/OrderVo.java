package com.orange.order.vo;

import com.orange.order.domain.OrderProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderVo {
    String orderId;

    Integer orderState;

    List<OrderProduct> orderProducts;

    /**
     * 实际付款
     */
    BigDecimal payMoney;

    Integer orderType;

    /**
     * 租赁开始时间
     */
    Long startTime;
    /**
     * 租赁结束时间
     */
    Long endTime;
    Integer orderMold;
    Long createTime;
    Integer  deliverType;
    public Long getDurationTime() {
        if (endTime!=null&&startTime!=null){
            return (endTime-startTime)/(3600*24)+1;

        }else {
            return null;
        }
    }
}
