package com.orange.person.constant;

public enum  AuthenticationGrade {
    ALL(0,"全部解决"),
    UNCERTIFIED(1,"未认证"),
    MANDATORY(2,"实名认证"),
    UNCERTIFIED_MANDATORY(3,"学生实名认证");
    String desc;
    int code;
    AuthenticationGrade(int code,String desc){
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
