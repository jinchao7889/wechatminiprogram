package com.orange.trip.constant;

public enum  QuestionsStatus {

    ALL(0,"全部解决"),
    UNSOLVED(1,"未解决"),
    RESOLVED(2,"已解决");
    String desc;
    int code;
    QuestionsStatus(int code,String desc){
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
