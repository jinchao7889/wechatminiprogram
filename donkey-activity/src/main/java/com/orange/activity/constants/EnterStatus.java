package com.orange.activity.constants;

public enum EnterStatus {
    ALL(0,"所有"),
    NO_PAY(1,"未付款"),
    ONGOING(2,"进行中,已付款"),
    PEOPLE_FULL(3,"已结束"),
    CANCEL(4,"已取消");
    String desc;
    int code;
    EnterStatus(int code,String desc){
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
