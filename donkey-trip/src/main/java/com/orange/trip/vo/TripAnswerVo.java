package com.orange.trip.vo;

import lombok.Data;

@Data
public class TripAnswerVo {
    Integer id;
    /**
     * 回答者的头像
     */
    String answerHead;
    /**
     * 回答者的昵称
     */
    String answerNickname;

    Long tripId;

    Long questionId;

    /**
     * 回答者的用户ID
     */
    String answerUserId;

    String content;
    /**
     * 评论父级ID
     */
    Integer parentId;

    /**
     * 评论次级ID
     * 被回答者的ID
     */
    Integer secondaryId;
    /**
     * 回复数量
     */
    Integer replyNumber=0;
    /**
     * 回复时间
     */
    Long createTime;
    /**
     * 被回答者的头像
     */
    String toAnswerHead;
    /**
     * 被回答者的昵称
     */
    String toAnswerNickname;

    /**
     * 是否点赞
     */
    Boolean isFabulous;
}
