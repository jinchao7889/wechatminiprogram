package com.orange.share.exception;

public class ParentRuntimeException extends RuntimeException {
    protected String code;
    protected String msg;
    public ParentRuntimeException(String message ){
        super(message);
        code="500";
        this.msg=message;
    }

    public ParentRuntimeException(String code, String message ){
        super(message);
        this.msg=message;
        this.code=code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
