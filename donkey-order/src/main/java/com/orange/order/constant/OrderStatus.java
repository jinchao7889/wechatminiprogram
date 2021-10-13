package com.orange.order.constant;

public enum  OrderStatus {
    ALL(0,"全部"),
    UNPAID(10,"未付款"),
    TO_BE_SHIPPED(20,"已付款-待发货"),
    RECEIVED(30,"待收货"),
    TO_BE_EVALUSATED(40,"待评价"),
    CANCEL(50,"取消"),
    CLOSE(60,"完成");
    String desc;
    int code;
    OrderStatus(int code,String desc){
        this.code=code;
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }
}
