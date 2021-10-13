package com.orange.shop.constants;

public enum  DeliverType {
    ALL(0,"全部解决"),
    SELF_TAKING(1,"自取"),
    MAIL(2,"邮寄"),
    CASH_ON_DELIVERY(3,"货到付款"),
    NO_DELIVERY(4,"虚拟商品无需配送");
    String desc;
    int code;
    DeliverType(int code,String desc){
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
