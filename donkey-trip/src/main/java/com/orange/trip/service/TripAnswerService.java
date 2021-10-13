package com.orange.trip.service;

import com.orange.share.vo.PageVo;
import com.orange.trip.info.AnswerInfo;
import com.orange.trip.info.AnswerPageInfo;
import com.orange.trip.vo.TripAnswerVo;

public interface TripAnswerService {
    TripAnswerVo addAnswer(AnswerInfo answerInfo);
    PageVo getAnswerPage(AnswerPageInfo answerPageInfo);
}
