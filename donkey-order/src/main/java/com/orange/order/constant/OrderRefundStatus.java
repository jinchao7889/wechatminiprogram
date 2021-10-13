package com.orange.order.constant;

public enum OrderRefundStatus {
    ALL("所有",0),
    IS_REFUND("退款处理中",1),
    REFUND_SUCCESS("退款成功",2),
    REFUND_FAIL("退款失败",3);

    String name;
    int value;
    OrderRefundStatus(String name, int value){
        this.name=name;
        this.value=value;
    }
    public static OrderRefundStatus getType(int value){
        for (OrderRefundStatus v:OrderRefundStatus.values()){
            if (v.value==value){
                return v;
            }
        }
        return null;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
