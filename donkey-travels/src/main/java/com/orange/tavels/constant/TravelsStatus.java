package com.orange.tavels.constant;

public enum  TravelsStatus {
    ALL(0,"全部"),
    EDITOR(1,"编辑中"),
    RELEASE(2,"已发布-待审核"),
    EXAMINE_SUCESS(3,"审核成功"),
    EXAMINE_FAIL(4,"审核失败"),
    CANCEL(5,"取消发布");
    String desc;
    int code;
    TravelsStatus(int code,String desc){
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
