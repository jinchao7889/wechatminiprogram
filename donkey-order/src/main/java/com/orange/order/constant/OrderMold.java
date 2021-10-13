package com.orange.order.constant;

public enum  OrderMold {
    ALL("所有",0),
    ORDINARY("普通商品型",1),
    TOUR("旅游型订单",2);

    String name;
    int value;
    OrderMold(String name, int value){
        this.name=name;
        this.value=value;
    }
    public static PaymentType getType(int value){
        for (PaymentType v: PaymentType.values()){
            if (v.value==value){
                return v;
            }
        }
        return null;
    }
}
