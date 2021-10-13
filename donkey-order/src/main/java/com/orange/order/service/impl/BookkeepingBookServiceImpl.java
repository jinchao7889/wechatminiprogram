package com.orange.order.service.impl;

import com.orange.order.config.WxPayConfig;
import com.orange.order.constant.OrderRefundStatus;
import com.orange.order.constant.OrderStatus;
import com.orange.order.dao.OrderDao;
import com.orange.order.dao.OrderRefundDao;
import com.orange.order.domain.Order;
import com.orange.order.domain.OrderRefund;
import com.orange.order.service.BookkeepingBookService;
import com.orange.share.util.JsonUtil;
import com.orange.share.util.PayUtil;
import com.orange.share.util.RandomStringUtil;
import com.orange.share.util.WxRefoundUtil;
import com.orange.share.wxconfig.ServiceProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BookkeepingBookServiceImpl implements BookkeepingBookService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderRefundDao orderRefundDao;
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Override
    public Map WXPay(String userId, String openid, String spbill_create_ip, String orderId, BigDecimal totalReward, String notify_url,String productName,String time_expire ) throws Exception {
        //TODO 生成的随机字符串
        String nonce_str = RandomStringUtil.getRandomStringByLength(32);
        //TODO 商品名称
        String body = productName;
        //TODO 获取客户端的ip地址
        //TODO 组装参数，用户生成统一下单接口的签名
        String total_fee=String.valueOf( totalReward.multiply(new BigDecimal(100)).intValue());
        log.error("支付金额:"+total_fee);

        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", ServiceProperty.WX_APP_ID);
        packageParams.put("mch_id", WxPayConfig.mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", orderId);//商户订单号
        packageParams.put("time_expire", time_expire);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
        packageParams.put("total_fee", total_fee);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);//支付成功后的回调地址
        packageParams.put("trade_type", WxPayConfig.TRADETYPE);//支付方式
        packageParams.put("openid", openid);
        String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

        //TODO MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
        String mysign = PayUtil.sign(prestr, WxPayConfig.key, "utf-8").toUpperCase();

        //TODO 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
        String xml = "<xml>" + "<appid>" + ServiceProperty.WX_APP_ID + "</appid>"
                + "<body><![CDATA[" + body + "]]></body>"
                + "<mch_id>" + WxPayConfig.mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<notify_url>" + notify_url + "</notify_url>"
                + "<openid>" + openid + "</openid>"
                + "<out_trade_no>" + orderId + "</out_trade_no>"
                + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                + "<time_expire>" + time_expire + "</time_expire>"
                + "<total_fee>" + total_fee + "</total_fee>"
                + "<trade_type>" + WxPayConfig.TRADETYPE + "</trade_type>"
                + "<sign>" + mysign + "</sign>"
                + "</xml>";
        System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);

        //TODO 调用统一下单接口，并接受返回的结果
        String result = PayUtil.httpRequest(WxPayConfig.pay_url, "POST", xml);

        System.out.println("调试模式_统一下单接口 返回XML数据：" + result);

        //TODO 将解析结果存储在HashMap中

        Map map = PayUtil.doXMLParse(result);

        String return_code = (String) map.get("return_code");//返回状态码

        Map<String, Object> response = new HashMap<String, Object>();//返回给小程序端需要的参数
        if (return_code == "SUCCESS" || return_code.equals(return_code)) {
            String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
            response.put("nonceStr", nonce_str);
            response.put("package", "prepay_id=" + prepay_id);
            Long timeStamp = System.currentTimeMillis() / 1000;
            response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
            //TODO 拼接签名需要的参数
            String stringSignTemp = "appId=" + ServiceProperty.WX_APP_ID + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
            //TODO 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
            String paySign = PayUtil.sign(stringSignTemp, WxPayConfig.key, "utf-8").toUpperCase();
            log.error("paySign:"+paySign);
            response.put("paySign", paySign);
        }else {
            log.error("申请微信支付orderId:{}返回异常,错误原因",orderId,result);
            throw new RuntimeException("订单支付时,微信返回了错误代码");
        }
        response.put("appid", ServiceProperty.WX_APP_ID);
        return response;
    }

    @Override
    public int WXRefund(String userId, String orderId, BigDecimal refundMoney, BigDecimal orderMoney, String refund_notify_url, BigDecimal out_refund_no) {
        if (refundMoney.compareTo(BigDecimal.ZERO)!=1){
            refundMoney=new BigDecimal(0.01);
        }
        //TODO 生成的随机字符串
        String nonce_str = RandomStringUtil.getRandomStringByLength(32);
        //TODO 商品名称
        String body = "快递";
        //TODO 获取客户端的ip地址
        //TODO 组装参数，用户生成统一下单接口的签名
        log.info("退款金额:{}",refundMoney);
        log.info("订单金额:{}",orderMoney);
        String refund_fee=String.valueOf( refundMoney.multiply(new BigDecimal(100)).intValue());
        String total_fee=String.valueOf( orderMoney.multiply(new BigDecimal(100)).intValue());

        log.info("退款金额:"+refund_fee);
        String out_refund_no_s=String.valueOf( out_refund_no.multiply(new BigDecimal(100)).intValue());

        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", ServiceProperty.WX_APP_ID);
        packageParams.put("mch_id", WxPayConfig.mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("out_trade_no", orderId);//商户订单号
        packageParams.put("total_fee", total_fee);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
        packageParams.put("out_refund_no", out_refund_no_s);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
        packageParams.put("refund_fee", refund_fee);//支付金额，这边需要转成字符串类型，否则后面的签名会失败
        packageParams.put("notify_url", refund_notify_url);//支付成功后的回调地址
        String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        //TODO MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
        String mysign = PayUtil.sign(prestr, WxPayConfig.key, "utf-8").toUpperCase();

        //TODO 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
        String xml = "<xml>" + "<appid>" + ServiceProperty.WX_APP_ID + "</appid>"
                + "<mch_id>" + WxPayConfig.mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<notify_url>" + refund_notify_url + "</notify_url>"
                + "<out_trade_no>" + orderId + "</out_trade_no>"
                + "<total_fee>" + total_fee + "</total_fee>"
                + "<refund_fee>" + refund_fee + "</refund_fee>"
                + "<out_refund_no>" + out_refund_no_s + "</out_refund_no>"
                + "<sign>" + mysign + "</sign>"
                + "</xml>";

        System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);

        String result="";
        //TODO 将解析结果存储在HashMap中
        Map resultMap = null;
        //TODO 调用统一下单接口，并接受返回的结果
        try {
            result = WxRefoundUtil.WeixinSendPost(xml,WxPayConfig.mch_id,WxPayConfig.refund_url);
            //TODO 解析返回的xml
            resultMap = PayUtil.doXMLParse(result);
        }catch (Exception e){
            log.error("用户userId:{},在退款时XML解析失败,错误原因{},返回的数据{}",userId,e.getMessage(),result);
            throw new RuntimeException("退款失败");
        }

        String return_code = (String) resultMap.get("return_code");//返回状态码
        log.info("return_code:{}", JsonUtil.objectToString(resultMap));
        if ("SUCCESS".equals(return_code) ) {
            log.info("用户userId:{}申请退款成功,订单号{},金额{}",userId,orderId,total_fee);

            return 1;
        }else {
            log.error("用户userId:{},在退款时微信返回错误码,错误为{},return_code:{},resultMap:{}",userId,result,return_code, JsonUtil.objectToString(resultMap));
            throw new RuntimeException("退款失败");
        }
    }

    @Override
    public Boolean paySuccess(Map<String, String> map) {
        String outTradeNo = map.get("out_trade_no");
        log.error("微信回调返回商户订单号："+outTradeNo);
        Order order=orderDao.findOne(outTradeNo);
        order.setOrderState(OrderStatus.TO_BE_SHIPPED.getCode());
        orderDao.saveAndFlush(order);
        this.rabbitTemplate.convertAndSend("orderPaySuccess", outTradeNo);
        return true;
    }

    @Override
    public void wxRefundSuccess(String orderId) {
        OrderRefund orderRefund= orderRefundDao.queryByOrderId(orderId);
        orderRefundDao.updateStatus(orderRefund.getId(), OrderRefundStatus.REFUND_SUCCESS.getValue());
    }
}
