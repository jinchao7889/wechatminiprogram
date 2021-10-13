package com.orange.order.controller;

import com.orange.order.service.BookkeepingBookService;
import com.orange.share.util.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static com.orange.share.util.PayUtil.doXMLParse;

@Slf4j
@RestController
@RequestMapping("/bookkeeping_book")
public class BookkeepingBookController {
    @Autowired
    BookkeepingBookService bookkeepingBookService;
    @RequestMapping(value = "/weixin/api/wxRefundNotify", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public void reRefundWX(HttpServletRequest request, HttpServletResponse response)  {
        try {
            String inputLine = "";
            String notityXml = "";

            while((inputLine = request.getReader().readLine()) != null){
                notityXml += inputLine;
            }
            //关闭流
            request.getReader().close();
            log.error("微信回调内容信息："+notityXml);
            //解析成Map
            Map<String,String> map = doXMLParse(notityXml);
            //判断 支付是否成功
            if("SUCCESS".equals(map.get("return_code"))){
                log.info("微信退款通知成功{}",notityXml);
                String a= map.get("req_info");
                String b = AESUtil.decryptData(a);
                Map<String,String> map2 = doXMLParse(b);
                bookkeepingBookService.wxRefundSuccess(map2.get("out_trade_no"));

            }else {
                log.error("微信退款通知失败{}",notityXml);
            }
        }catch (Exception e){
            log.error("返回微信的通知出现异常,错误为:{}",e.getMessage());

        }finally {
            //封装 返回值
            StringBuffer buffer = new StringBuffer();
            buffer.append("<xml>");
            buffer.append("<return_code>SUCCESS</return_code>");
            buffer.append("<return_msg>OK</return_msg>");
            buffer.append("</xml>");

            //给微信服务器返回 成功标示 否则会一直询问 咱们服务器 是否回调成功
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
            } catch (IOException e) {
                log.error("返回微信的通知出现写入异常,异常为:{}",e.getMessage());
            }
            //返回
            writer.print(buffer.toString());
        }


    }

    @RequestMapping(value = "/weixin/api/wxNotify", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public void reWX(HttpServletRequest request,HttpServletResponse response)   {
        try{
            String inputLine = "";
            String notityXml = "";

            while((inputLine = request.getReader().readLine()) != null){
                notityXml += inputLine;
            }
            //关闭流
            request.getReader().close();
            log.error("微信回调内容信息："+notityXml);
            //解析成Map
            Map<String,String> map = doXMLParse(notityXml);
            //判断 支付是否成功
            if("SUCCESS".equals(map.get("return_code"))){
                log.info("微信支付通知成功：是");

                //获得 返回的商户订单号
                bookkeepingBookService.paySuccess(map);
                //访问DB
            }else {

                log.info("微信支付通知失败,错误原因{}",notityXml);
            }
        }catch (Exception e){
            log.error("返回微信的通知出现异常,错误为:{}",e.getMessage());
        }finally {
            //封装 返回值
            StringBuffer buffer = new StringBuffer();
            buffer.append("<xml>");
            buffer.append("<return_code>SUCCESS</return_code>");
            buffer.append("<return_msg>OK</return_msg>");
            buffer.append("</xml>");
            //给微信服务器返回 成功标示 否则会一直询问 咱们服务器 是否回调成功
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
            } catch (IOException e) {
                log.error("返回微信的通知出现写入异常,异常为:{}",e.getMessage());
            }
            //返回
            writer.print(buffer.toString());
        }
    }
}
