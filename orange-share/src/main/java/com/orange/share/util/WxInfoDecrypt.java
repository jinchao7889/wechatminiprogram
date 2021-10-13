package com.orange.share.util;


import net.sf.json.JSONObject;

public class WxInfoDecrypt {
    public static String getInfo(String wxspAppid,String wxspSecret, String jsCode,String data, String session_key,String iv) throws Exception {
        String grant_type = "authorization_code";
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + jsCode + "&grant_type="
                + grant_type;
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
// 解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        String result = AesCbcUtil.decrypt(data, session_key, iv, "UTF-8");

        return result;
    }
}
