package com.orange.trip.service;

import com.orange.share.vo.PageVo;
import com.orange.trip.domain.TripQuestions;
import com.orange.trip.info.TripQuestionOwnerPageInfo;
import com.orange.trip.info.TripQuestionPageInfo;
import com.orange.trip.info.TripQuestionsInfo;
import com.orange.trip.vo.TripQuestionsVo;

public interface TripQuestionsService {
    TripQuestionsVo addQuestion(TripQuestionsInfo tripQuestions);
    PageVo getQuestion(TripQuestionPageInfo pageInfo);

    PageVo getOwnerQuestion(TripQuestionPageInfo pageInfo);
    void delQuestion(Integer questionsId);

    /**
     * 问题回复数量加1
     */
    void questionReplyNumberPlus(Long questionsId);

    /**
     * 问题回复数量减1
     */
    void questionReplyNumberReduce(Long questionsId);
     TripQuestionsVo coverQuestion(TripQuestions questions);
}
