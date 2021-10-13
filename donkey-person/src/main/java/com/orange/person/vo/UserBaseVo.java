package com.orange.person.vo;

import lombok.Data;

@Data
public class UserBaseVo {
    String id;
    String nickname;
    String userPhone;
    String portraitUrl;
    Long createTime;
    boolean accountNonExpired=false;
    boolean accountNonLocked=false;
    boolean credentialsNonExpired=false;
    boolean enabled=false;
    Integer gender;
    Long lastLoginTime;
    Integer userGrade=0;
    Integer authenticationGrade=1;
}
