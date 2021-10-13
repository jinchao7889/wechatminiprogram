package com.orange.trip.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "trip_answer")
@Data
public class TripAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "trip_id")
    Integer tripId;

    @Column(name = "question_id")
    Long questionId;

    /**
     * 回答者的用户ID
     */
    @Column(name = "answer_user_id")
    String answerUserId;

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
