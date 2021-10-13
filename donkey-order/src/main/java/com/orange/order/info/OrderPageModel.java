package com.orange.order.info;

import com.orange.share.page.PageInfo;

import javax.validation.constraints.NotNull;


public class OrderPageModel extends PageInfo {

    private String orderId;
    /**
     * 订单状态
     */
    @NotNull
    private Integer orderStatus;

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
