package com.orange.order.constant;

import lombok.Getter;

@Getter
public enum PaymentType {
    ALL("所有",0),
    BALANCE_PAID("余额支付",1),
    WX_PAID("微信支付",2);

    String name;
    int value;
    PaymentType(String name, int value){
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
