package com.orange.security.rbac;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.orange.security.core.validate.code.sms.SmsCodeSender;
import net.sf.json.JSONException;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;


public class TxSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) throws Exception {
        int appid = 1400224673; // SDK AppID 以1400开头

// 短信应用 SDK AppKey
        String appkey = "507695c2a7ac80e36307eb2126c59a6b";



// 短信模板 ID，需要在短信应用中申请
        int templateId = 359198; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请

// 签名
        String smsSign = "易驴户外"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请

        try {
            String[] params = {code};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", mobile,
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result);
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }
    }
}
