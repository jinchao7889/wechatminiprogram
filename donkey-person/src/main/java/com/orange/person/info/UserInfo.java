package com.orange.person.info;

import lombok.Data;

@Data
public class UserInfo {
    private Integer gender;
    private String nickname;
    private String city;
    private String province;
    private String country;
    private String portraitUrl;
    private Integer trafficVolume;

    /**
     * 用户等级
     */

    private Integer userGrade;

    /**
     * 关注量
     */
    private Integer followVolume;
    private Integer authenticationGrade;
}
