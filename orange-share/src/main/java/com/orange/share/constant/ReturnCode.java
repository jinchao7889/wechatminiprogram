package com.orange.share.constant;


public enum ReturnCode {
    SUCCESS("0000","查询成功"),
    NODATA("0001","查询成功无记录"),
    FEAILED("0002","查询失败"),

    DELETE_SUCCESS("2000","删除成功"),
    DELETE_NODATA("2001","删除无记录"),
    DELETE_FEAILED("2002","删除失败"),

    UPDATE_SUCCESS("3000","更新成功"),
    UPDATE_NODATA("3001","更新无记录"),
    UPDATE_FEAILED("3002","更新失败"),

    ACCOUNT_ERROR("1000", "账户不存在或被禁用"),
    API_NOT_EXISTS("1001", "请求的接口不存在"),
    API_NOT_PER("1002", "没有该接口的访问权限"),
    PARAMS_ERROR("1004", "参数为空或格式错误"),
    SIGN_ERROR("1005", "数据签名错误"),
    AMOUNT_NOT_QUERY("1010", "余额不够"),
    API_DISABLE("1011", "查询权限已被限制"),
    UNKNOWN_IP("1099", "非法IP请求"),
    SYSTEM_ERROR("9999", "系统异常"),

    //TODO 订单类异常
    ORDER_RECEIPT_GENDER_ERROR("4001","限制性别接单"),
    ORDER_RECEIPT_NULL("4002","订单已经被抢,或者订单不存在"),
    ORDER_RECEIPT_OWN("4003","不能接自己的单子"),
    ORDER_RECEIPT_NUMBER("4004","接单数量已经达到上限"),

    BALANCE_INSUFFCIENT("4101","余额不足");

    private String code;
    private String msg;
    public String getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

    ReturnCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    @Override
    public String toString() {
        return "{code:"+code+",msg:"+msg+"}";
    }
}
