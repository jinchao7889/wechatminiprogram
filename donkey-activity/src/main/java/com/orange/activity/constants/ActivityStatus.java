package com.orange.activity.constants;

public enum ActivityStatus {
    ALL(0,"所有"),
    ONGOING(1,"报名中"),
    TIME_END(2,"已截止"),
    PEOPLE_FULL(3,"已满员"),
    COMPLETED(4,"完成");
    String desc;
    int code;
    ActivityStatus(int code,String desc){
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
