package com.orange.tavels.vo;

import lombok.Data;

@Data
public class TravelsVo {
    String title;

    /**
     * 封面
     */
    String coverMap;

    /**
     * 头像
     */
    String head;

    /**
     * 昵称
     */
    String nickname;

    /**
     * 浏览量
     */
    Long browseVolume;


    Long releaseTime;
    String travelsId;

    int fabulousVolume;
    String  travelDestination;
    int commentVolume;
    Integer travelsStatus;
}
