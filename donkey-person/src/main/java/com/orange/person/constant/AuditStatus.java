package com.orange.person.constant;

public enum AuditStatus {
    ALL(0,"全部解决"),
    NO_AUDIT(1,"正在审核"),
    AUDIT_SUCCESS(2,"审核成功"),
    AUDIT_FAIL(3,"审核失败");
    String desc;
    int code;
    AuditStatus(int code,String desc){
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
