package com.orange.order.config;

public class WxPayConfig {
    //微信支付的商户id
    public static final String mch_id = "1451706102";
    //微信支付的商户密钥
    public static final String key = "077a0fbc0d9348da889Dcb4af97873d3";
    //支付成功后的服务器回调url
    public static final String notify_url = "https://www.easylv.cn/api/bookkeeping_book/weixin/api/wxNotify";
    //签名方式，固定值
    public static final String SIGNTYPE = "MD5";
    //交易类型，小程序支付的固定值为JSAPI
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //微信退款地址
    public static final String refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    //微信退款回调地址
    public static final String refund_notify_url = "https://www.easylv.cn/api/bookkeeping_book/weixin/api/wxRefundNotify";
}
