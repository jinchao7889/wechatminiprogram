package com.orange.activity.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "activity_leave_message")
public class ActivityLeaveMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "activity_id")
    String activityId;
    /**
     * 回复者的用户ID
     */
    @Column(name = "answer_user_id")
    String answerUserId;
    @Column(name = "to_answer_user_id")
    String toAnswerUserId;
    String content;
    /**
     * 评论父级ID
     */
    @Column(name = "parent_id")
    Integer parentId;

    /**
     * 评论次级ID
     */
    @Column(name = "secondary_id")
    Integer secondaryId;
    /**
     * 回复数量
     */
    @Column(name = "reply_number")
    Integer replyNumber=0;

    /**
     * 点赞数量
     */
    @Column(name = "like_number")
    Integer likeNumber=0;
    /**
     * 回复时间
     */
    @Column(name = "create_time")
    Long createTime;


    /**
     * 是否删除
     */
    Boolean enable=true;
}
