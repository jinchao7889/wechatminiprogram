package com.orange.shop.constants;

/**
 * 可选等级的类型
 */
public enum  OptionalGradeType {

    ALL(0,"全部解决"),
    OPTIONAL(1,"可选"),
    MANDATORY(2,"必选"),
    UNCERTIFIED_MANDATORY(3,"认证后必选");
    String desc;
    int code;
    OptionalGradeType(int code,String desc){
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
