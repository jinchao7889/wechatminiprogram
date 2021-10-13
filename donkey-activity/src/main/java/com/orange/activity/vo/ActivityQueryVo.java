package com.orange.activity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActivityQueryVo   {
    private String id;
    private String title;

    public ActivityQueryVo(){

    }

    public ActivityQueryVo(String id,String title){
        this.id = id;
        this.title = title;
    }
}
