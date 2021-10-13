package com.orange.share.util;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class HttpClientUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final HttpClient client = HttpClients.createDefault();
    /**
     * post请求
     * @param post  	post
     * @param paramStr  json格式的请求体数据
     * @return			响应的数据(json)
     */
    public static String post(HttpPost post, String paramStr, String cookie){
        String data="";
        post.addHeader("Content-type","application/json; charset=utf-8");
        post.setHeader("Accept", "application/json");
        if(StringUtils.isNotEmpty(paramStr)) {
            post.setEntity(new StringEntity(paramStr, Charset.forName("UTF-8")));
        }
        if (StringUtils.isNotEmpty(cookie)) {
            post.setHeader("Cookie", cookie);
        }
        try {
            HttpResponse resp = client.execute(post);
            //获取HTTP状态码
            int statusCode = resp.getStatusLine().getStatusCode();
            if(statusCode!=200){
                throw new Exception("网络错误,状态码:"+statusCode);
            }
            HttpEntity httpEntity = resp.getEntity();
            data = EntityUtils.toString(httpEntity, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("post获取数据异常",e);
        }finally {
            post.releaseConnection();
        }
        return data;
    }
    /**
     * put请求
     * @param put  	put
     * @param paramStr  json格式的请求体数据
     * @return			响应的数据(json)
     */
    public static String put(HttpPut put, String paramStr, String cookie){

        String data="";
        put.addHeader("Content-type","application/json; charset=utf-8");
        put.setHeader("Accept", "application/json");
        if (StringUtils.isNotEmpty(cookie)) {
            put.setHeader("Cookie", cookie);
        }
        put.setEntity(new StringEntity(paramStr, Charset.forName("UTF-8")));
        try {
            HttpResponse resp = client.execute(put);
            //获取HTTP状态码
            int statusCode = resp.getStatusLine().getStatusCode();
            if(statusCode!=200){
                throw new Exception("网络错误,状态码:"+statusCode);
            }
            HttpEntity httpEntity = resp.getEntity();
            data = EntityUtils.toString(httpEntity, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("put获取数据异常",e);
        }finally {
            put.releaseConnection();
        }
        return data;
    }
    /**
     * get请求
     * @param get  get
     * @return     响应的数据(json)
     */
    public static String get(HttpGet get, String cookie){
        String data="";
        get.addHeader("Content-type","application/json; charset=utf-8");
        get.setHeader("Accept", "application/json");
        if (StringUtils.isNotEmpty(cookie)) {
            get.setHeader("Cookie", cookie);
        }
        try {
            HttpResponse resp = client.execute(get);
            //获取HTTP状态码
            int statusCode = resp.getStatusLine().getStatusCode();
            if(statusCode!=200){
                throw new Exception("网络错误,状态码:"+statusCode);
            }
            HttpEntity httpEntity = resp.getEntity();
            data = EntityUtils.toString(httpEntity, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("get获取数据异常",e);
        }finally {
            get.releaseConnection();
        }
        return data;
    }
    /**
     * delete请求
     * @param delete  delete
     * @return     响应的数据(json)
     */
    public static String delete(HttpDelete delete, String cookie){
        String data="";
        delete.addHeader("Content-type","application/json; charset=utf-8");
        delete.setHeader("Accept", "application/json");
        if (StringUtils.isNotEmpty(cookie)) {
            delete.setHeader("Cookie", cookie);
        }
        try {
            HttpResponse resp = client.execute(delete);
            //获取HTTP状态码
            int statusCode = resp.getStatusLine().getStatusCode();
            if(statusCode!=200){
                throw new Exception("网络错误,状态码:"+statusCode);
            }
            HttpEntity httpEntity = resp.getEntity();
            data = EntityUtils.toString(httpEntity, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("get获取数据异常",e);
        }finally {
            delete.releaseConnection();
        }
        return data;
    }


}
