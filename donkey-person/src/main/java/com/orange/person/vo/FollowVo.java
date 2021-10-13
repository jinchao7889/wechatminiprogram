package com.orange.person.vo;

import lombok.Data;

@Data
public class FollowVo {
    Integer id;
    String beConcernedUserId;
    String nickname;
    String portraitUrl;
}
