package com.orange.order.service;

import java.math.BigDecimal;
import java.util.Map;

public interface BookkeepingBookService {
    /**
     * 微信支付
     * @param userId 提交订单用户的Id
     * @param openid 小程序的openId
     * @param spbill_create_ip IP
     * @param orderId 商户订单ID
     * @param totalReward 支付金额
     * @param notify_url 支付回调URL
     * @return
     * @throws Exception
     */
    Map WXPay(String userId,  String openid, String spbill_create_ip, String orderId, BigDecimal totalReward, String notify_url,String productName,String time_expire) throws Exception;

    /**
     * 微信退款
     * @param userId 提交订单用户的Id
     * @param orderId 商户订单ID
     * @param refundMoney 退款金额
     * @param orderMoney 订单金额
     * @param refund_notify_url 退款回调URL
     * @param out_refund_no 退款单号
     * @return
     */
    int WXRefund(String userId, String orderId, BigDecimal refundMoney, BigDecimal orderMoney,String refund_notify_url, BigDecimal out_refund_no);

    Boolean paySuccess(Map<String,String> map);

    public void wxRefundSuccess(String orderId);
}
