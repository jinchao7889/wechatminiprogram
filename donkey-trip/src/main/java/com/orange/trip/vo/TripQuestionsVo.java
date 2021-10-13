package com.orange.trip.vo;

import lombok.Data;

@Data
public class TripQuestionsVo {
    Long id;
    Long tripId;
    String questionUserId;
    /**
     * 作者头像
     */
    String userHead;
    /**
     * 作者昵称
     */
    String userNickname;

    String question;

    /**
     * 创建时间
     */
    Long createTime;

    /**
     * 是否加精
     */
    Boolean isBoutique=false;

    /**
     * 是否解决
     */
    Integer solveStatus;

    Integer replyNumber=0;

}
