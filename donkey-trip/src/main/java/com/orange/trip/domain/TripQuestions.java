package com.orange.trip.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "trip_questions")
public class TripQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "trip_id")
    Integer tripId;

    /**
     * 问题创建者Id
     */
    @Column(name = "question_user_id",length = 32)
    String questionUserId;
    /**
     * 问题
     */
    String question;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    Long createTime;

    /**
     * 是否加精
     */
    @Column(name = "is_boutique")
    Boolean isBoutique=false;

    /**
     * 解决状态
     */
    @Column(name = "is_solve")
    Integer solveStatus;
    @Column(name = "reply_number")
    Integer replyNumber=0;

    Boolean enable=true;
}
