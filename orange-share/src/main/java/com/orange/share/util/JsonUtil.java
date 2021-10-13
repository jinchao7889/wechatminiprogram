package com.orange.share.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public  class  JsonUtil {
    public static String listToString (List list){
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(list);
    }    public static String objectToString(Object object){
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(object);
    }
    public static String objectToStringNotNull(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static String listToStringNotNull (List list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static Map<String,Object> jsonStrToMap(String str){
        Gson gson = new Gson();
        Map<String,Object> map=new LinkedHashMap<>();
        return gson.fromJson(str,map.getClass());
    }

}
